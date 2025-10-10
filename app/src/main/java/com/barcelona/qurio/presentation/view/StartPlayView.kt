package com.barcelona.qurio.presentation.view

import com.barcelona.qurio.base.BaseView
import com.barcelona.qurio.presentation.model.Question

interface StartPlayView : BaseView {
    fun showQuestions(questions: List<Question>)
    fun showQuestion(question: Question, questionNumber: String)
    fun showAnswers(answers: List<String>)
    fun highlightAnswers(correctAnswer: String, selectedPosition: Int)
    fun resetAnswers()
    fun updateTimer(secondsLeft: Long, progress: Float)
    fun onTimerFinished()
    fun showMessage(message: String)
    fun showEndOfQuestions()
    fun showLoading()
    fun hideLoading()
    fun showError(error: Throwable)
}