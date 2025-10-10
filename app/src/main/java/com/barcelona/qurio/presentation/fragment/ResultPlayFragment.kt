package com.barcelona.qurio.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseFragment
import com.barcelona.qurio.databinding.FragmentResultPlayBinding

class ResultPlayFragment : BaseFragment<FragmentResultPlayBinding>() {
    override val layoutIdFragment: Int
        get() = R.layout.fragment_result_play

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_resultPlayFragment_to_startPlayFragment)
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_resultPlayFragment_to_startPlayFragment)
        }
    }

}