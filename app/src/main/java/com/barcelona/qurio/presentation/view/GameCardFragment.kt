package com.barcelona.qurio.presentation.view

import android.os.Bundle
import android.view.View
import com.barcelona.qurio.R
import com.barcelona.qurio.data.dto.GameCardList
import com.barcelona.qurio.databinding.FragmentGameCardBinding
import com.barcelona.qurio.presentation.adapter.gamecardAdapter.GameCardAdapter

class GameCardFragment() : BaseFragment<FragmentGameCardBinding>() {

    override val layoutIdFragment: Int
        get() = R.layout.fragment_game_card

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerView
        val adapter = GameCardAdapter(GameCardList)

        recyclerView.adapter = adapter

    }

}