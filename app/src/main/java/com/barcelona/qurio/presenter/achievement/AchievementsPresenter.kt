package com.barcelona.qurio.presenter.achievement

import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.presentation.view.HomeView
import com.barcelona.qurio.presenter.repository.AchievementRepository
import com.barcelona.qurio.presenter.repository.TriviaGameSessionRepository
import com.barcelona.qurio.presenter.repository.UserStatsRepository
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds


class AchievementsPresenter @Inject constructor(
    private val achievementRepository: AchievementRepository,
    private val triviaGameSessionRepository: TriviaGameSessionRepository,
    private val userStatsRepository: UserStatsRepository
) : BasePresenter<HomeView>() {

    fun getAllAchievements() {
        tryToCall(
            block = { achievementRepository.getAllAchievements() },
            onSuccess = { achievements ->
                view?.showAchievements(achievements)
            }
        )
    }

    fun getAchievement(achievementId: Int) {
        tryToCall(
            block = { achievementRepository.getAchievementById(achievementId) },
            onSuccess = { achievement ->
                view?.showCurrentAchievement(achievement)
            }
        )
    }

    fun calculateAchievements() {
        tryToCall(block = ::calculatedAchievementsOfUser)
    }

    private suspend fun calculatedAchievementsOfUser() {
        val gamesSession = triviaGameSessionRepository.getAllSessions()
        val userState = userStatsRepository.getPreferences()

        // 1 First Spark
        val allAnsweredQuestionsOfGame = gamesSession.map { it.correctAnswers }
        val firstSparkIsLocked = achievementRepository.getAchievementById(id = 1).isLocked
        if (allAnsweredQuestionsOfGame.any { it == 1 } && firstSparkIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 1, isLocked = false)
        }


        // 2 Quick Thinker
        val currentStreak = gamesSession.last().streakAnswers
        val quickThinkerIsLocked = achievementRepository.getAchievementById(id = 2).isLocked
        if (currentStreak >= 5 && quickThinkerIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 2, isLocked = false)
        }

        // 3 Knowledge Seeker
        val isTotalAnsweredQuestionsMoreThanOrEqualTwenty =
            gamesSession.map { it.correctAnswers }.any { it >= 20 }
        val knowledgeSeekerIsLocked = achievementRepository.getAchievementById(id = 3).isLocked
        if (isTotalAnsweredQuestionsMoreThanOrEqualTwenty && knowledgeSeekerIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 3, isLocked = false)
        }

        // 4 - Explorer
        val playedCategories = gamesSession.map { it.category }.toSet()
        val explorerIsLocked = achievementRepository.getAchievementById(id = 4).isLocked
        if (playedCategories.size >= 4 && explorerIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 4, isLocked = false)
        }

        // 5 Trivia Wanderer
        val isCompletePrefectGame = gamesSession.any { it.correctAnswers == 12 }
        val triviaWandererIsLocked = achievementRepository.getAchievementById(id = 5).isLocked
        if (isCompletePrefectGame && triviaWandererIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 5, isLocked = false)
        }

        // 6 Lightning Brain
        // Condition: Answer 5 questions correctly in under 30 seconds total
        val game = gamesSession.last()
        val correctInTimeCount = game.correctAnswers
        val totalTimeTaken = game.totalTimeSeconds.seconds
        val lightningBrainIsLocked = achievementRepository.getAchievementById(id = 6).isLocked
        if (correctInTimeCount >= 5 && totalTimeTaken <= 30.seconds && lightningBrainIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 6, isLocked = false)
        }

        // 7 Never Give Up
        val retriedQuizSuccessCount = gamesSession.map { it.category }.let { categoryList ->
            categoryList.size - categoryList.toSet().size
        }
        val neverGiveUpIsLocked = achievementRepository.getAchievementById(id = 7).isLocked
        if (retriedQuizSuccessCount >= 1 && neverGiveUpIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 7, isLocked = false)
        }

        // 8 - Untouchable
        val gamesStreak = gamesSession.map { it.streakAnswers }
        val untouchableIsLocked = achievementRepository.getAchievementById(id = 8).isLocked
        if (gamesStreak.any { it == 10 } && untouchableIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 8, isLocked = false)
        }

        // 9 Brainstorm Hero
        val totalCorrectAnswers = gamesSession.sumOf { it.correctAnswers }
        val brainstormHeroIsLocked = achievementRepository.getAchievementById(id = 9).isLocked
        if (totalCorrectAnswers >= 100 && brainstormHeroIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 9, isLocked = false)
        }

        // 10 Quiz Champion
        val totalPoints = userState.points
        val quizChampionIsLocked = achievementRepository.getAchievementById(id = 10).isLocked
        if (totalPoints >= 500 && quizChampionIsLocked) {
            achievementRepository.setAchievementLocking(achievementId = 10, isLocked = false)
        }

        // 11 Legend of Trivia
        val isAchievementsUnlockedCompleted = achievementRepository.getAllAchievements().size == 10
        if (isAchievementsUnlockedCompleted) {
            achievementRepository.setAchievementLocking(achievementId = 11, isLocked = false)
        }

    }


    fun updateAchievements() {
        tryToCall(
            block = {
                val unlockedAchievementCount =
                    achievementRepository.getAllAchievements().filter { it.isLocked.not() }.size
                userStatsRepository.updateRewards(unlockedAchievementCount)
            }
        )
    }

}