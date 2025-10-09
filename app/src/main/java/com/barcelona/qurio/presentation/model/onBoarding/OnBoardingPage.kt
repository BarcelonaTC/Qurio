package com.barcelona.qurio.presentation.model.onBoarding

import com.barcelona.qurio.R

data class OnboardingPage(
    val imageRes: Int,
    val title: Int,
    val description: Int
) {
    companion object {
        fun values(): List<OnboardingPage> {
            return listOf(
                OnboardingPage(
                    imageRes = R.drawable.brain_image,
                    title = R.string.welcome_to_qurio,
                    description = R.string.welcome_to_the_world_of_qurio_where_questions_spark_curiosity_and_prizes_await_the_smartest_ready_to_begin_the_challenge
                ),
                OnboardingPage(
                    imageRes = R.drawable.crown_image,
                    title = R.string.challenge_and_win,
                    description = R.string.answer_quickly_earn_points_and_share_with_your_friends
                ),
                OnboardingPage(
                    imageRes = R.drawable.cup_image,
                    title = R.string.collect_them_all,
                    description = R.string.unlock_characters_earn_badges_and_climb_the_leaderboards_qurio_is_fascinating_but_you_can_handle_it
                ),
            )
        }
    }
}
