package com.barcelona.qurio.presenter

import android.os.Build
import android.os.CountDownTimer
import androidx.annotation.RequiresApi
import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.model.Question
import com.barcelona.qurio.presentation.model.TriviaGameSession
import com.barcelona.qurio.presentation.view.StartPlayView
import com.barcelona.qurio.presenter.repository.AchievementRepository
import com.barcelona.qurio.presenter.repository.TriviaGameRepository
import com.barcelona.qurio.presenter.repository.TriviaGameSessionRepository
import com.barcelona.qurio.presenter.repository.UserStatsRepository
import com.barcelona.qurio.presenter.repository.VolumeLevelRepository
import java.net.UnknownHostException
import javax.inject.Inject
import kotlin.math.abs
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

@RequiresApi(Build.VERSION_CODES.O)
class StartPlayPresenter @Inject constructor(
    private val triviaGameRepository: TriviaGameRepository,
    private val gameSessionRepository: TriviaGameSessionRepository,
    private val userStatsRepository: UserStatsRepository,
    private val volumeLevelRepository: VolumeLevelRepository,
    private val achievementRepository: AchievementRepository,
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

    private var currentStreakAnswers = 0
    private var maxStreakAnswer = 0

    fun getTotalLives() {
        tryToCall(
            block = { userStatsRepository.getPreferences().lives },
            onSuccess = { view?.showTotalLives(it) },
        )
    }

    fun getQuestions(categoryId: Int, categoryName: String, difficulty: String) {
        categoryTitle = categoryName
        tryToCall(
            block = { triviaGameRepository.fetchQuestions(12, difficulty, "multiple", categoryId) },
            onStart = { view?.showLoading() },
            onSuccess = ::onQuestionsSuccess,
            onError = ::handleError,
            onEnd = { view?.hideLoading() }
        )
    }

    fun getMusicVolumeLevel() {
        tryToCall(
            block = volumeLevelRepository::getMusicVolumeLevel,
            onSuccess = { view?.getMusicVolumeLevel(it) }
        )
    }

    fun getSoundVolumeLevel() {
        tryToCall(
            block = volumeLevelRepository::getSoundVolumeLevel,
            onSuccess = { view?.getSoundVolumeLevel(it) }
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
            add(q.correctAnswer)
            addAll(q.incorrectAnswers)
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

    fun onCheckButtonClicked(selectedPosition: Int?) {
        if (!questionChecked) {
            if (selectedPosition == null) {
                view?.showMessage("Select an answer first")
                return
            }

            val question = questions[currentIndex]
            val correct = question.correctAnswer
            view?.highlightAnswers(correct, selectedPosition)
            questionChecked = true
            countDownTimer?.cancel()

            val answer = currentAnswers.getOrNull(selectedPosition)
            if (answer == correct) {
                currentStreakAnswers++
                if (currentStreakAnswers > maxStreakAnswer) {
                    maxStreakAnswer = currentStreakAnswers
                }
                correctCount++
            } else {
                currentStreakAnswers = 0
                wrongCount++
            }

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
        if (questions.isEmpty()) return 0
        val percentage = correctCount.toFloat() / questions.size
        return when {
            percentage >= 1.0f -> 3
            percentage >= 2f / 3f -> 2
            percentage >= 1f / 3f -> 1
            else -> 0
        }
    }

    private fun calculatePoints(currentPoints: Int): Int {
        if (calculateStars() == 0) {
            val loss = when (correctCount) {
                in 1..2 -> -Random.nextInt(5, 12)
                3 -> -Random.nextInt(15, 30)
                else -> -Random.nextInt(10, 15)
            }
            return if (currentPoints + loss < 0) -currentPoints else loss
        }
        var earned = 0
        repeat(correctCount) {
            earned += Random.nextInt(500, 1000)
        }
        return earned
    }

    private fun handleLivesAfterGame(oldPoints: Int, newPoints: Int) {
        if (calculateStars() == 0 && oldPoints > 0 && newPoints == 0) return
        if (calculateStars() == 0 && oldPoints == 0 && newPoints == 0) {
            tryToCall(
                block = { userStatsRepository.decreaseLives(1) },
                onSuccess = { getTotalLives() },
                onError = { view?.showError(it) }
            )
        }
    }

    private fun saveGameSession() {
        val skipped = (questions.size - (correctCount + wrongCount))

        tryToCall(
            block = {
                val currentPoints = userStatsRepository.getPreferences().points
                val delta = calculatePoints(currentPoints)
                val newPoints = updateUserPoints(currentPoints, delta)
                val session = buildGameSession(skipped, delta)

                persistSessionAndAchievements(session)

                Triple(session, currentPoints, newPoints)
            },
            onSuccess = { (session, currentPoints, newPoints) ->
                view?.onGameSessionSaved(session)
                handleLivesAfterGame(currentPoints, newPoints)
                getTotalLives()
            },
            onError = { view?.showError(it) }
        )
    }

    private suspend fun updateUserPoints(currentPoints: Int, delta: Int): Int {
        val newPoints = (currentPoints + delta).coerceAtLeast(0)

        if (delta > 0) {
            userStatsRepository.increasePoints(delta)
        } else if (delta < 0) {
            userStatsRepository.decreasePoints(abs(delta))
        }

        return newPoints
    }

    private fun buildGameSession(skipped: Int, delta: Int): TriviaGameSession {
        return TriviaGameSession(
            correctAnswers = correctCount,
            wrongAnswers = wrongCount,
            skippedAnswers = skipped,
            stars = calculateStars(),
            totalTimeSeconds = totalTimeSeconds.toInt(),
            earnedCoins = delta,
            category = categoryTitle,
            streakAnswers = maxStreakAnswer
        )
    }

    private suspend fun persistSessionAndAchievements(session: TriviaGameSession) {
        gameSessionRepository.insertSession(session)
        calculatedAchievementsOfUser()
    }

    private suspend fun calculatedAchievementsOfUser() {
        val gamesSession = gameSessionRepository.getAllSessions()
        val userState = userStatsRepository.getPreferences()

        // 1 First Spark
        val allAnsweredQuestionsOfGame = gamesSession.map { it.correctAnswers }
        val firstSparkIsLocked = achievementRepository.getAchievementById(id = 1).isLocked
        if (allAnsweredQuestionsOfGame.any { it >= 1 } && firstSparkIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 1, isLocked = false)
            updateAchievements()
        }


        // 2 Quick Thinker
        val currentStreak = gamesSession.last().streakAnswers
        val quickThinkerIsLocked = achievementRepository.getAchievementById(id = 2).isLocked
        if (currentStreak >= 5 && quickThinkerIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 2, isLocked = false)
            updateAchievements()
        }

        // 3 Knowledge Seeker
        val isTotalAnsweredQuestionsMoreThanOrEqualTwenty =
            gamesSession.map { it.correctAnswers }.any { it >= 20 }
        val knowledgeSeekerIsLocked = achievementRepository.getAchievementById(id = 3).isLocked
        if (isTotalAnsweredQuestionsMoreThanOrEqualTwenty && knowledgeSeekerIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 3, isLocked = false)
            updateAchievements()
        }

        // 4 - Explorer
        val playedCategories = gamesSession.map { it.category }.toSet()
        val explorerIsLocked = achievementRepository.getAchievementById(id = 4).isLocked
        if (playedCategories.size >= 4 && explorerIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 4, isLocked = false)
            updateAchievements()
        }

        // 5 Trivia Wanderer
        val isCompletePrefectGame = gamesSession.any { it.correctAnswers == 12 }
        val triviaWandererIsLocked = achievementRepository.getAchievementById(id = 5).isLocked
        if (isCompletePrefectGame && triviaWandererIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 5, isLocked = false)
            updateAchievements()
        }

        // 6 Lightning Brain
        val game = gamesSession.last()
        val correctInTimeCount = game.correctAnswers
        val totalTimeTaken = game.totalTimeSeconds.seconds
        val lightningBrainIsLocked = achievementRepository.getAchievementById(id = 6).isLocked
        if (correctInTimeCount >= 5 && totalTimeTaken <= 30.seconds && lightningBrainIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 6, isLocked = false)
            updateAchievements()
        }

        // 7 Never Give Up
        val retriedQuizSuccessCount = gamesSession.map { it.category }.let { categoryList ->
            categoryList.size - categoryList.toSet().size
        }
        val neverGiveUpIsLocked = achievementRepository.getAchievementById(id = 7).isLocked
        if (retriedQuizSuccessCount >= 1 && neverGiveUpIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 7, isLocked = false)
            updateAchievements()
        }

        // 8 - Untouchable
        val gamesStreak = gamesSession.map { it.streakAnswers }
        val untouchableIsLocked = achievementRepository.getAchievementById(id = 8).isLocked
        if (gamesStreak.any { it == 10 } && untouchableIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 8, isLocked = false)
            updateAchievements()
        }

        // 9 Brainstorm Hero
        val totalCorrectAnswers = gamesSession.sumOf { it.correctAnswers }
        val brainstormHeroIsLocked = achievementRepository.getAchievementById(id = 9).isLocked
        if (totalCorrectAnswers >= 100 && brainstormHeroIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 9, isLocked = false)
            updateAchievements()
        }

        // 10 Quiz Champion
        val totalPoints = userState.points
        val quizChampionIsLocked = achievementRepository.getAchievementById(id = 10).isLocked
        if (totalPoints >= 2000 && quizChampionIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 10, isLocked = false)
            updateAchievements()
        }

        // 11 Legend of Trivia
        val isAchievementsUnlockedCompleted = achievementRepository.getAllAchievements().size == 10
        if (isAchievementsUnlockedCompleted) {
            achievementRepository.setAchievementLocking(achievementId = 11, isLocked = false)
            updateAchievements()
        }

    }

    private suspend fun updateAchievements() {
        val unlockedAchievementCount =
            achievementRepository.getAllAchievements().filter { it.isLocked.not() }.size
        userStatsRepository.updateRewards(unlockedAchievementCount)
    }

    private fun handleError(error: Throwable) {
        when (error) {
            is UnknownHostException -> view?.showError(error)
            else -> view?.showError(error)
        }
    }

    fun destroyTimer() {
        countDownTimer?.cancel()
    }
}