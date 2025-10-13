package com.barcelona.qurio.presenter

import android.os.CountDownTimer
import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.model.Question
import com.barcelona.qurio.presentation.model.TriviaGameSession
import com.barcelona.qurio.presentation.view.StartPlayView
import com.barcelona.qurio.presenter.repository.TriviaGameRepository
import com.barcelona.qurio.presenter.repository.TriviaGameSessionRepository
import javax.inject.Inject

class StartPlayPresenter @Inject constructor(
    private val triviaGameRepository: TriviaGameRepository,
    private val gameSessionRepository: TriviaGameSessionRepository
) : BasePresenter<StartPlayView>() {

    private var questions: List<Question> = emptyList()
    private var currentIndex = 0
    private var currentAnswers: List<String> = emptyList()
    private var countDownTimer: CountDownTimer? = null
    private val questionTimeMillis = 20_000L
    private var questionChecked = false

    private var correctCount = 0
    private var wrongCount = 0
    private var totalTimeSeconds = 0L
    private var timerStartTime = 0L

    fun getQuestions(categoryId: Int) {
        tryToCall(
            block = { triviaGameRepository.fetchQuestions(12, "easy", "multiple", categoryId) },
            onStart = { view?.showLoading() },
            onSuccess = ::onQuestionsSuccess,
            onError = { view?.showError(it) },
            onEnd = { view?.hideLoading() }
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

            override fun onFinish() {
                view?.updateTimer(0, 0f)
                view?.onTimerFinished()
                nextQuestion()
            }
        }.start()
    }

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
        return when {
            correctCount == questions.size -> 3
            correctCount >= questions.size / 2 -> 2
            correctCount > 0 -> 1
            else -> 0
        }
    }

    private fun calculateCoins(): Int = correctCount * 10

    private fun saveGameSession() {
        val skipped = (questions.size - (correctCount + wrongCount))
        val session = TriviaGameSession(
            correctAnswers = correctCount,
            wrongAnswers = wrongCount,
            skippedAnswers = skipped,
            stars = calculateStars(),
            totalTimeSeconds = totalTimeSeconds.toInt(),
            earnedCoins = calculateCoins()
        )
        tryToCall(
            block = { gameSessionRepository.insertSession(session) },
            onStart = {},
            onSuccess = { view?.onGameSessionSaved(session) },
            onError = { view?.showError(it) },
            onEnd = {}
        )
    }

    fun destroyTimer() {
        countDownTimer?.cancel()
    }
}