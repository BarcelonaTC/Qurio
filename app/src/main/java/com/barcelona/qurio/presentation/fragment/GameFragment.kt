package com.barcelona.qurio.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.barcelona.qurio.QurioApp
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseFragment
import com.barcelona.qurio.databinding.FragmentGameBinding
import com.barcelona.qurio.model.dto.gameCards
import com.barcelona.qurio.presentation.adapter.gamecardAdapter.GameCardsAdapter
import com.barcelona.qurio.presentation.model.gamecard.GameCardModel
import com.barcelona.qurio.presentation.sounds.SoundPlayerManager

class GameFragment() : BaseFragment<FragmentGameBinding>() {

    override val layoutIdFragment: Int
        get() = R.layout.fragment_game
    private lateinit var soundManager: SoundPlayerManager


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        soundManager = (requireActivity().application as QurioApp).soundPlayerManager

        val recyclerView = binding.recyclerView
        val adapter = GameCardsAdapter(gameCards, onPlayClick = ::onPlayNowClicked)
        recyclerView.adapter = adapter
        setUpClickListeners()
    }

    fun onPlayNowClicked(gameCard: GameCardModel) {
        val dialog = DifficultyLevelFragment()
        dialog.show(parentFragmentManager, "DifficultyLevelDialog")
        parentFragmentManager.setFragmentResultListener(
            "level_game_type",
            viewLifecycleOwner
        ) { _, bundle ->
            val levelType = bundle.getString("levelType")
            findNavController().navigate(
                GameFragmentDirections.actionGameFragmentToStartPlayFragment(
                    gameCard.categoryId,
                    gameCard.title
                )
            )
        }
    }

    private fun setUpClickListeners() {
        binding.appBar.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}