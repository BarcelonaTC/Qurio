package com.barcelona.qurio.presentation.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.barcelona.qurio.QurioApp
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseFragment
import com.barcelona.qurio.databinding.FragmentLastGamesBinding
import com.barcelona.qurio.presentation.adapter.lastGame.LastGameAdapter
import com.barcelona.qurio.presentation.model.LastGame
import com.barcelona.qurio.presentation.sounds.SoundPlayerManager
import com.barcelona.qurio.presentation.view.LastGamesView
import com.barcelona.qurio.presenter.LastGamesPresenter
import jakarta.inject.Inject

class LastGamesFragment : BaseFragment<FragmentLastGamesBinding>(), LastGamesView {
    override val layoutIdFragment: Int
        get() = R.layout.fragment_last_games

    @Inject
    lateinit var presenter: LastGamesPresenter
    private lateinit var soundManager: SoundPlayerManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as QurioApp).appComponent.inject(this)
        presenter.attachView(this)
        soundManager = (requireActivity().application as QurioApp).soundPlayerManager

        setInteractionListeners()
        setLastGames(context)
        presenter.getLastGames()
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter.destroyPresenter()
        super.onDestroy()
    }

    private fun setLastGames(context: Context?) {
        binding.lastGamesRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun showLastGames(games: List<LastGame>) {
        binding.lastGamesRecyclerView.apply {
            adapter = LastGameAdapter(games)
        }
    }

    private fun setInteractionListeners() {
        binding.appBar.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}