package com.barcelona.qurio.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.barcelona.qurio.QurioApp
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseFragment
import com.barcelona.qurio.databinding.FragmentResultPlayBinding
import com.barcelona.qurio.presenter.repository.TriviaGameSessionRepository
import javax.inject.Inject

class ResultPlayFragment : BaseFragment<FragmentResultPlayBinding>() {
    override val layoutIdFragment: Int
        get() = R.layout.fragment_result_play

    @Inject
    lateinit var gameSessionRepository: TriviaGameSessionRepository
    private val args: ResultPlayFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity().application as QurioApp).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        binding.btnPlayAgain.setOnClickListener {
            findNavController().popBackStack(R.id.startPlayFragment, inclusive = false)
        }
        val result = args.session
        val session = args.session
        binding.results.apply {
            setCorrectCount(result.correctAnswers.toString())
            setIncorrectCount(result.wrongAnswers.toString())
            setSkippedCount(result.skippedAnswers.toString())
            setReward(result.earnedCoins.toString())
            showBlur(true)
            showStars(true)
            animateStars()
        }.updateResult(
            correctAnswers = session.correctAnswers,
            totalQuestions = session.correctAnswers + session.wrongAnswers + session.skippedAnswers
        )
    }
}