package com.barcelona.qurio.presentation.fragment

import android.os.Bundle
import android.view.View
import com.barcelona.qurio.QurioApp
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseFragment
import com.barcelona.qurio.databinding.FragmentStartPlayBinding
import com.barcelona.qurio.model.dto.TriviaQuestionDto
import com.barcelona.qurio.presentation.view.StartPlayView
import com.barcelona.qurio.presenter.StartPlayPresenter
import javax.inject.Inject

class StartPlayFragment() : BaseFragment<FragmentStartPlayBinding>(), StartPlayView {
    override val layoutIdFragment: Int
        get() = R.layout.fragment_start_play

    @Inject
    lateinit var startPlayPresenter: StartPlayPresenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity().application as QurioApp).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)
        startPlayPresenter.attachView(this)
        startPlayPresenter.getQuestions()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        startPlayPresenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        startPlayPresenter.destroyPresenter()
    }

    override fun showLoading() {
        binding.loadingLayout.visibility = View.VISIBLE
        binding.contentLayout.visibility = View.VISIBLE
        binding.errorLayout.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.loadingLayout.visibility = View.GONE
        binding.contentLayout.visibility = View.VISIBLE
        binding.errorLayout.visibility = View.GONE
    }

    override fun showError(error: Throwable) {
        binding.loadingLayout.visibility = View.GONE
        binding.contentLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
    }

    override fun getQuestions(questions: List<TriviaQuestionDto?>) {
        //  binding.text.text = questions.get(0)?.question
    }
}