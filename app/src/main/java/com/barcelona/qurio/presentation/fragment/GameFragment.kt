package com.barcelona.qurio.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseFragment
import com.barcelona.qurio.databinding.FragmentGameBinding
import com.barcelona.qurio.model.dto.gameCards
import com.barcelona.qurio.presentation.adapter.gamecardAdapter.GameCardsAdapter
import com.barcelona.qurio.presentation.model.gamecard.GameCardModel

class GameFragment() : BaseFragment<FragmentGameBinding>() {

    override val layoutIdFragment: Int
        get() = R.layout.fragment_game

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        val adapter = GameCardsAdapter(gameCards, onPlayClick = ::onPlayNowClicked)
        recyclerView.adapter = adapter
    }

    fun onPlayNowClicked(gameCard: GameCardModel) {
        findNavController().navigate(
            GameFragmentDirections.actionGameFragmentToStartPlayFragment(gameCard.categoryId)
        )
    }
}