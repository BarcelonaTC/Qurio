package com.barcelona.qurio.presenter

import com.barcelona.qurio.base.BasePresenter
import com.barcelona.qurio.model.api.TriviaApiService
import com.barcelona.qurio.presentation.view.StartPlayView
import javax.inject.Inject

class StartPlayPresenter @Inject constructor(
    private val triviaApiService: TriviaApiService
) : BasePresenter<StartPlayView>(){
     fun getQuestions() {
       tryToCall(
           block = { triviaApiService.getQuestions(20, null, null, null, null, null) },
           onStart = { view?.showLoading() },
           onSuccess = { view?.getQuestions(it.results.orEmpty()) },
           onError = { view?.showError(it) },
           onEnd = { view?.hideLoading() }
       )
    }
}