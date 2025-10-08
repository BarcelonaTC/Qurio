package com.barcelona.qurio.presentation.view

import android.os.Bundle
import android.view.View
import com.barcelona.qurio.R
import com.barcelona.qurio.data.dto.GameCardList
import com.barcelona.qurio.databinding.FragmentGameBinding
import com.barcelona.qurio.presentation.adapter.gamecardAdapter.GameCardsAdapter

class GameFragment() : BaseFragment<FragmentGameBinding>() {

    override val layoutIdFragment: Int
        get() = R.layout.fragment_game

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        val adapter = GameCardsAdapter(GameCardList)

        recyclerView.adapter = adapter

    }

}