package com.barcelona.qurio.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseFragment
import com.barcelona.qurio.databinding.FragmentHomeBinding
import com.barcelona.qurio.model.dto.GameCardList
import com.barcelona.qurio.presentation.adapter.gamecardAdapter.GameCardsAdapter
import com.barcelona.qurio.presentation.adapter.streakAdapter.StreakDayAdapter
import com.barcelona.qurio.presentation.animation.createGameCardTransformer
import com.barcelona.qurio.presentation.model.gamecard.GameCardModel
import com.barcelona.qurio.presentation.model.streak.DayStreak
import com.barcelona.qurio.presentation.model.streak.StreakModel
import com.barcelona.qurio.presentation.view.HomeView

class HomeFragment() : BaseFragment<FragmentHomeBinding>(), HomeView {
    override val layoutIdFragment: Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setStreak(this.context)
        setupGameCardPager()

    }

    private fun setStreak(context: Context?) {
        binding.streakComponent.daysRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.streakComponent.daysRecyclerView.apply {
            adapter = StreakDayAdapter(
                listOf(
                    DayStreak("S", true),
                    DayStreak("M", false),
                    DayStreak("T", false),
                    DayStreak("W", false),
                    DayStreak("Th", false),
                    DayStreak("F", false),
                    DayStreak("S", false),
                )
            )
        }
        binding.streakComponent.streak = StreakModel(
            title = "1 day streak, start make a series",
            subtitle = "Every day count!",
            listOf(
                DayStreak("S", true),
                DayStreak("M", false),
                DayStreak("T", false),
                DayStreak("W", false),
                DayStreak("Th", false),
                DayStreak("F", false),
                DayStreak("S", false),
            )
        )
    }

    private fun setupGameCardPager() {
        val adapter = GameCardsAdapter(GameCardList, ::onPlayNowClicked)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.recyclerView.offscreenPageLimit = 3

        binding.recyclerView.setPageTransformer(createGameCardTransformer())

        (binding.recyclerView.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
    }
    fun onPlayNowClicked(gameCard: GameCardModel){
        findNavController().navigate(R.id.startPlayFragment)
    }
}