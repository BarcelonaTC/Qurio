package com.barcelona.qurio.service

import com.barcelona.qurio.presentation.model.streak.DayStreak
import com.barcelona.qurio.presentation.model.streak.StreakModel
import jakarta.inject.Inject
import java.util.Calendar

class UserStreakService @Inject constructor(private val dao: UserStreakDao) {

    suspend fun updateStreak() {
        val now = System.currentTimeMillis()
        val today = getDayOfWeek(now)

        var streak = dao.getStreak()

        if (streak == null) {
            streak = UserStreak(
                id = 1,
                currentStreak = 1,
                longestStreak = 1,
                lastActiveDate = now,
                weekStartDate = getWeekStart(now),
                totalDaysActive = 1
            ).setDayActive(today, true)

            dao.insertStreak(streak)
            return
        }

        if (streak.lastActiveDate != null && isSameDay(streak.lastActiveDate, now)) {
            return
        }

        var updatedStreak = streak
        if (shouldResetWeek(streak.weekStartDate, now)) {
            updatedStreak = streak.copy(
                monday = false,
                tuesday = false,
                wednesday = false,
                thursday = false,
                friday = false,
                saturday = false,
                sunday = false,
                weekStartDate = getWeekStart(now)
            )
        }

        val newStreak = when {
            streak.lastActiveDate == null -> 1
            isConsecutiveDay(streak.lastActiveDate, now) -> streak.currentStreak + 1
            else -> 1
        }

        val newLongestStreak = maxOf(newStreak, streak.longestStreak)

        updatedStreak = updatedStreak.copy(
            currentStreak = newStreak,
            longestStreak = newLongestStreak,
            lastActiveDate = now,
            totalDaysActive = streak.totalDaysActive + 1
        ).setDayActive(today, true)

        dao.updateStreak(updatedStreak)
    }

    suspend fun getStreakInfo(): StreakModel {
        val streak = dao.getStreak() ?: return StreakModel(
            title = "0 day streak, start make a series",
            subtitle = "Every day count!",
            days = listOf(
                DayStreak("S", false),
                DayStreak("M", false),
                DayStreak("T", false),
                DayStreak("W", false),
                DayStreak("Th", false),
                DayStreak("F", false),
                DayStreak("S", false)
            )
        )

        val now = System.currentTimeMillis()
        val isStreakActive = streak.lastActiveDate?.let {
            isSameDay(it, now) || isYesterday(it, now)
        } ?: false

        return StreakModel(
            title = "${streak.currentStreak} day streak, make a big series",
            subtitle = if (isStreakActive) "Keep it up!" else "Start make a series",
            days = listOf(
                DayStreak("S", streak.sunday),
                DayStreak("M", streak.monday),
                DayStreak("T", streak.tuesday),
                DayStreak("W", streak.wednesday),
                DayStreak("Th", streak.thursday),
                DayStreak("F", streak.friday),
                DayStreak("S", streak.saturday),
            )
        )
    }

    private fun getDayOfWeek(timestamp: Long): DayOfWeek {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
        }
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.MONDAY -> DayOfWeek.MONDAY
            Calendar.TUESDAY -> DayOfWeek.TUESDAY
            Calendar.WEDNESDAY -> DayOfWeek.WEDNESDAY
            Calendar.THURSDAY -> DayOfWeek.THURSDAY
            Calendar.FRIDAY -> DayOfWeek.FRIDAY
            Calendar.SATURDAY -> DayOfWeek.SATURDAY
            else -> DayOfWeek.SUNDAY
        }
    }

    private fun isSameDay(timestamp1: Long, timestamp2: Long): Boolean {
        val cal1 = Calendar.getInstance().apply { timeInMillis = timestamp1 }
        val cal2 = Calendar.getInstance().apply { timeInMillis = timestamp2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    private fun isYesterday(timestamp: Long, now: Long): Boolean {
        val cal = Calendar.getInstance().apply {
            timeInMillis = now
            add(Calendar.DAY_OF_YEAR, -1)
        }
        return isSameDay(timestamp, cal.timeInMillis)
    }

    private fun isConsecutiveDay(lastActive: Long, now: Long): Boolean {
        return isYesterday(lastActive, now)
    }

    private fun getWeekStart(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    private fun shouldResetWeek(weekStartDate: Long, now: Long): Boolean {
        val currentWeekStart = getWeekStart(now)
        return weekStartDate < currentWeekStart
    }

    private fun UserStreak.setDayActive(day: DayOfWeek, active: Boolean): UserStreak {
        return when (day) {
            DayOfWeek.MONDAY -> copy(monday = active)
            DayOfWeek.TUESDAY -> copy(tuesday = active)
            DayOfWeek.WEDNESDAY -> copy(wednesday = active)
            DayOfWeek.THURSDAY -> copy(thursday = active)
            DayOfWeek.FRIDAY -> copy(friday = active)
            DayOfWeek.SATURDAY -> copy(saturday = active)
            DayOfWeek.SUNDAY -> copy(sunday = active)
        }
    }
}

enum class DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}