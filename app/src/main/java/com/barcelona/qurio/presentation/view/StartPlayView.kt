package com.barcelona.qurio.presentation.view

import com.barcelona.qurio.base.BaseView
import com.barcelona.qurio.model.dto.TriviaQuestionDto

interface StartPlayView : BaseView {
    fun getQuestions(questions: List<TriviaQuestionDto?>)
}