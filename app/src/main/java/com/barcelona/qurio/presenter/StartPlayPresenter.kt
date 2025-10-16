package com.barcelona.qurio.presenter

import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.model.Question
import com.barcelona.qurio.presentation.model.TriviaGameSession
import com.barcelona.qurio.presentation.view.StartPlayView
import com.barcelona.qurio.presenter.repository.TriviaGameRepository
import com.barcelona.qurio.presenter.repository.TriviaGameSessionRepository
import com.barcelona.qurio.presenter.repository.UserStatsRepository
import com.barcelona.qurio.presenter.repository.VolumeLevelRepository
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.math.abs
import kotlin.random.Random

class StartPlayPresenter @Inject constructor(
    private val triviaGameRepository: TriviaGameRepository,
    private val gameSessionRepository: TriviaGameSessionRepository,
    private val userStatsRepository: UserStatsRepository,
    private val volumeLevelRepository: VolumeLevelRepository
) : BasePresenter<StartPlayView>() {

    private var questions: List<Question> = emptyList()
    private var currentIndex = 0
    private var currentAnswers: List<String> = emptyList()
    private var countDownTimer: CountDownTimer? = null
    private val questionTimeMillis = 50_000L
    private var questionChecked = false

    private var correctCount = 0
    private var wrongCount = 0
    private var totalTimeSeconds = 0L
    private var timerStartTime = 0L

    private var categoryTitle = ""

    fun getTotalLives() {
        tryToCall(
            block = { userStatsRepository.getPreferences().lives },
            onSuccess = { view?.showTotalLives(it) },
        )
    }

    fun getQuestions(categoryId: Int, categoryName: String) {
        categoryTitle = categoryName
        tryToCall(
            block = { triviaGameRepository.fetchQuestions(12, "easy", "multiple", categoryId) },
            onStart = { view?.showLoading() },
            onSuccess = ::onQuestionsSuccess,
            onError = ::handleError,
            onEnd = { view?.hideLoading() }
        )
    }

    fun getMusicVolumeLevel() {
        tryToCall(
            block = volumeLevelRepository::getMusicVolumeLevel,
            onSuccess = {view?.getMusicVolumeLevel(it)}
        )
    }

    fun getSoundVolumeLevel() {
        tryToCall(
            block = volumeLevelRepository::getSoundVolumeLevel,
            onSuccess = {view?.getSoundVolumeLevel(it)}
        )
    }

    private fun onQuestionsSuccess(list: List<Question>) {
        questions = list
        view?.hideLoading()
        if (list.isNotEmpty()) {
            view?.showQuestions(list)
            showCurrentQuestion()
        } else {
            view?.showMessage("No questions found")
        }
    }

    private fun showCurrentQuestion() {
        questionChecked = false
        val q = questions[currentIndex]
        currentAnswers = mutableListOf<String>().apply {
            q.correctAnswer?.let { add(it) }
            q.incorrectAnswers?.let { addAll(it.filterNotNull()) }
            shuffle()
        }
        view?.showQuestion(q, "Q ${currentIndex + 1}/${questions.size}")
        view?.showAnswers(currentAnswers)
        view?.resetAnswers()
        val isLast = currentIndex == questions.size - 1
        view?.toggleSkipButton(!isLast)

        timerStartTime = System.currentTimeMillis()
        startTimer()
    }

    private fun startTimer() {
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(questionTimeMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                val progress = millisUntilFinished.toFloat() / questionTimeMillis
                view?.updateTimer(secondsLeft, progress)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onFinish() {
                view?.updateTimer(0, 0f)
                view?.onTimerFinished()
                nextQuestion()
            }
        }.start()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onCheckButtonClicked(selectedPosition: Int?) {
        if (!questionChecked) {
            if (selectedPosition == null) {
                view?.showMessage("Select an answer first")
                return
            }

            val question = questions[currentIndex]
            val correct = question.correctAnswer ?: ""
            view?.highlightAnswers(correct, selectedPosition)
            questionChecked = true
            countDownTimer?.cancel()

            val answer = currentAnswers.getOrNull(selectedPosition)
            if (answer == correct) correctCount++ else wrongCount++
            totalTimeSeconds += ((System.currentTimeMillis() - timerStartTime) / 1000).toInt()

            if (currentIndex == questions.size - 1) {
                saveGameSession()
                view?.showEndOfQuestions()
            }

        } else {
            if (currentIndex < questions.size - 1) {
                nextQuestion()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun nextQuestion() {
        countDownTimer?.cancel()
        if (currentIndex < questions.size - 1) {
            currentIndex++
            showCurrentQuestion()
        } else {
            saveGameSession()
            view?.showEndOfQuestions()
        }
    }

    private fun calculateStars(): Int {
        if (questions.isEmpty()) return 0
        val percentage = correctCount.toFloat() / questions.size
        return when {
            percentage >= 1.0f -> 3
            percentage >= 2f / 3f -> 2
            percentage >= 1f / 3f -> 1
            else -> 0
        }
    }

    private fun calculatePoints(): Int {
        if (calculateStars() == 0) {
            return when (correctCount) {
                //TODO this is real logic , but commented for test
//                in 1..2 -> Random.nextInt(5, 12)
//                3 -> Random.nextInt(15, 30)
//                else -> -Random.nextInt(10, 15)
                in 1..2 -> Random.nextInt(100, 400)
                3 -> Random.nextInt(400, 500)
                else -> Random.nextInt(50, 100)
            }
        }

        var earned = 0
        repeat(correctCount) {
            //  earned += Random.nextInt(8, 20)
            earned += Random.nextInt(500, 1000)
        }
        return earned
    }

    private fun handleLivesAfterGame() {
        if (calculateStars() == 0) {
            tryToCall(
                block = { userStatsRepository.decreaseLives(1) },
                onSuccess = { getTotalLives() },
                onError = { view?.showError(it) }
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveGameSession() {
        val skipped = (questions.size - (correctCount + wrongCount))
        val earnedPoints = calculatePoints()
        val session = TriviaGameSession(
            correctAnswers = correctCount,
            wrongAnswers = wrongCount,
            skippedAnswers = skipped,
            stars = calculateStars(),
            totalTimeSeconds = totalTimeSeconds.toInt(),
            earnedCoins = earnedPoints,
            category = categoryTitle,
        )
        tryToCall(
            block = {
                gameSessionRepository.insertSession(session)
                updateUserPoints(earnedPoints)
            },
            onStart = {},
            onSuccess = {
                view?.onGameSessionSaved(session)
                handleLivesAfterGame()

            },
            onError = { view?.showError(it) },
            onEnd = {}
        )
    }

    private suspend fun updateUserPoints(points: Int) {
        if (points > 0) {
            userStatsRepository.increasePoints(points)
        } else {
            userStatsRepository.decreasePoints(abs(points))
        }
    }
    private fun handleError(error: Throwable){
        when(error){
            is UnknownHostException -> view?.showError(error)
            else -> view?.showError(error)
        }
    }

    fun destroyTimer() {
        countDownTimer?.cancel()
    }
}