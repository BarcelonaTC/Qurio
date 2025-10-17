package com.barcelona.qurio.model.local

import com.barcelona.qurio.R
import com.barcelona.qurio.model.local.entity.AchievementEntity

object AchievementDataSource {
    val initialAchievements = listOf(
        AchievementEntity(
            imageRes = R.drawable.achievement_1,
            lockedImage = R.drawable.ic_achievement_1,
            title = "First Spark",
            description = "You answered your very first question! The journey begins. " +
                    "How to get:\nAnswer your first question in any category.",
            isLocked = true
        ),
        AchievementEntity(
            imageRes = R.drawable.achievement_2,
            lockedImage = R.drawable.ic_achievement_2,
            title = "Quick Thinker",
            description = "You nailed 5 questions in a row — brains on fire! " +
                    "How to get:\nAnswer 5 consecutive questions correctly in a single game.",
            isLocked = true
        ),
        AchievementEntity(
            imageRes = R.drawable.achievement_3,
            lockedImage = R.drawable.ic_achievement_3,
            title = "Knowledge Seeker",
            description = "Your curiosity knows no bounds! " +
                    "How to get:\nAnswer a total of 20 questions across any categories.",
            isLocked = true
        ),
        AchievementEntity(
            imageRes = R.drawable.achievement_4,
            lockedImage = R.drawable.ic_achievement_4,
            title = "Explorer",
            description = "You love experimenting and discovering new things! " +
                    "How to get:\nPlay in at least 4 different categories.",
            isLocked = true
        ),
        AchievementEntity(
            imageRes = R.drawable.achievement_5,
            lockedImage = R.drawable.ic_achievement_5,
            title = "Trivia Wanderer",
            description = "Your curiosity led you far and wide." +
                    "How to get:\nComplete at least one quiz game in every available difficulty level.",
            isLocked = true
        ),
        AchievementEntity(
            imageRes = R.drawable.achievement_6,
            lockedImage = R.drawable.ic_achievement_6,
            title = "Lightning Brain",
            description = "Speed is your superpower!\n" +
                    "How to get:\nAnswer 5 questions correctly in under 30 seconds total.",
            isLocked = true
        ),
        AchievementEntity(
            imageRes = R.drawable.achievement_7,
            lockedImage = R.drawable.ic_achievement_7,
            title = "Never Give Up",
            description = "You failed but tried again — true resilience! " +
                    "How to get:\nRetry a failed quiz and complete it successfully.",
            isLocked = true
        ),
        AchievementEntity(
            imageRes = R.drawable.achievement_8,
            lockedImage = R.drawable.ic_achievement_8,
            title = "Untouchable",
            description = "You rarely make mistakes — you dominate the game! " +
                    "How to get:\nCorrectly answer 10 consecutive questions in a single game without any mistakes.",
            isLocked = true
        ),
        AchievementEntity(
            imageRes = R.drawable.achievement_9,
            lockedImage = R.drawable.ic_achievement_9,
            title = "Brainstorm Hero",
            description = "Your memory and consistency are unmatched! " +
                    "How to get:\nReach 100 correct answers in total across all games.",
            isLocked = true
        ),
        AchievementEntity(
            imageRes = R.drawable.achievement_10,
            lockedImage = R.drawable.ic_achievement_10,
            title = "Quiz Champion",
            description = "You’ve proven yourself as a top-tier player. " +
                    "How to get:\nearn 2000 total points.",
            isLocked = true
        ),
        AchievementEntity(
            imageRes = R.drawable.achievement_11,
            lockedImage = R.drawable.ic_achievement_11,
            title = "Legend of Trivia",
            description = "You’ve unlocked every achievement — a true master of knowledge! " +
                    "How to get:\nUnlock all other achievements in the game.",
            isLocked = true
        )
    )
}