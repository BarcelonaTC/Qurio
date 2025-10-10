package com.barcelona.qurio.presentation.fragment

import android.os.Bundle
import android.view.View
import com.barcelona.qurio.QurioApp
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseFragment
import com.barcelona.qurio.databinding.FragmentOnboardingBinding
import com.barcelona.qurio.presentation.adapter.onBoardingAdapter.OnboardingAdapter
import com.barcelona.qurio.presentation.animation.setupSwipeUpGesture
import com.barcelona.qurio.presentation.animation.swipeUpAnimation
import com.barcelona.qurio.presentation.model.onBoarding.OnboardingPage
import com.barcelona.qurio.presentation.view.OnBoardingView
import com.barcelona.qurio.presenter.OnBoardingPresenter
import javax.inject.Inject

class OnBoardingFragment : BaseFragment<FragmentOnboardingBinding>(), OnBoardingView {

    override val layoutIdFragment: Int = R.layout.fragment_onboarding

    @Inject
    lateinit var presenter: OnBoardingPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectDependencies()
        presenter.attachView(this)

        setupViewPager()
        setupSwipeAnimation()
        setupListeners()
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter.destroyPresenter()
        super.onDestroy()
    }

    private fun injectDependencies() {
        (requireActivity().application as QurioApp).appComponent.inject(this)
    }

    private fun setupViewPager() {
        binding.viewPager.apply {
            adapter = OnboardingAdapter(OnboardingPage.values())
            isUserInputEnabled = false
        }
    }

    private fun setupSwipeAnimation() {
        swipeUpAnimation(binding.onboardingView)
        setupSwipeUpGesture(binding.swipeImage) { onSwipeUp() }
    }

    private fun setupListeners() = with(binding) {
        rightArrow.setOnClickListener { onRightArrowClicked() }
        leftArrow.setOnClickListener { onLeftArrowClicked() }
    }

    override fun onRightArrowClicked() {
        val pager = binding.viewPager
        val lastIndex = pager.adapter?.itemCount?.minus(1) ?: return

        if (pager.currentItem < lastIndex) {
            pager.currentItem++
            return
        }

        if (pager.currentItem == lastIndex) {
            presenter.setFirstLaunch()
        }
    }

    override fun onLeftArrowClicked() {
        val pager = binding.viewPager
        if (pager.currentItem > 0) pager.currentItem--
    }

    override fun onSwipeUp() {
        presenter.setFirstLaunch()
    }
}