package com.barcelona.qurio.presentation.view

import android.os.Bundle
import android.view.View
import com.barcelona.qurio.R
import com.barcelona.qurio.databinding.FragmentOnboardingBinding
import com.barcelona.qurio.presentation.adapter.onBoardingAdapter.OnboardingAdapter
import com.barcelona.qurio.presentation.animation.setupSwipeUpGesture
import com.barcelona.qurio.presentation.animation.swipeUpAnimation
import com.barcelona.qurio.presentation.model.onBoarding.OnboardingPage

class OnBoardingFragment() : BaseFragment<FragmentOnboardingBinding>() {
    override val layoutIdFragment: Int
        get() = R.layout.fragment_onboarding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = OnboardingAdapter(
            OnboardingPage.values()
        )
        swipeUpAnimation(binding.onboardingView)
        setupSwipeUpGesture(binding.swipeImage) {
            binding.viewPager.currentItem = binding.viewPager.currentItem + 1
        }
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.adapter = adapter
    }
}