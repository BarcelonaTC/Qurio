package com.barcelona.qurio.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.barcelona.qurio.QurioApp
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseFragment
import com.barcelona.qurio.databinding.FragmentStartPlayBinding
import com.barcelona.qurio.presentation.adapter.QuestionAdapter
import com.barcelona.qurio.presentation.model.Question
import com.barcelona.qurio.presentation.view.StartPlayView
import com.barcelona.qurio.presenter.StartPlayPresenter
import javax.inject.Inject

class StartPlayFragment : BaseFragment<FragmentStartPlayBinding>(), StartPlayView {

    override val layoutIdFragment: Int get() = R.layout.fragment_start_play

    @Inject
    lateinit var startPlayPresenter: StartPlayPresenter

    private var adapter: QuestionAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity().application as QurioApp).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)
        startPlayPresenter.attachView(this)
        startPlayPresenter.getQuestions()
        setupListeners()
    }

    private fun setupListeners() {
        binding.checkButton.setOnClickListener {
            startPlayPresenter.onCheckButtonClicked(adapter?.selectedPosition)
        }

        binding.skipButton.setOnClickListener {
            startPlayPresenter.nextQuestion()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        startPlayPresenter.detachView()
        startPlayPresenter.destroyTimer()
    }

    override fun showQuestions(questions: List<Question>) {
        binding.gameLayout.visibility = View.VISIBLE
    }

    override fun showQuestion(
        question: Question,
        questionNumber: String
    ) {
        binding.questionsPager.setQuestion(question.question ?: "")
        binding.questionsPager.setQuestionNumber(questionNumber)
    }

    override fun showAnswers(answers: List<String>) {
        adapter = QuestionAdapter(answers) {}
        binding.answersLayout.adapter = adapter
    }

    override fun highlightAnswers(correctAnswer: String, selectedPosition: Int) {
        val isCorrect = adapter?.answers?.getOrNull(selectedPosition) == correctAnswer
        binding.scoreIndicator.showScore(isCorrect)
        adapter?.correctAnswer = correctAnswer
        adapter?.showCorrect = true
        adapter?.notifyDataSetChanged()
        binding.checkButton.setText("Next")
        binding.skipButton.visibility = View.GONE
    }

    override fun resetAnswers() {
        binding.checkButton.setText("Check")
        binding.skipButton.visibility = View.VISIBLE
        adapter?.showCorrect = false
        adapter?.selectedPosition = null
        adapter?.notifyDataSetChanged()
    }

    override fun updateTimer(secondsLeft: Long, progress: Float) {
        binding.customTimeComponent.setProgress(progress)
        binding.customTimeComponent.setCenterText("${secondsLeft} Sec")
    }

    override fun onTimerFinished() {
        showToastMessage("Time's up!")
    }

    override fun showMessage(message: String) {
        showToastMessage(message)
    }

    override fun showEndOfQuestions() {
        binding.checkButton.visibility = View.GONE
        findNavController().navigate(R.id.action_startPlayFragment_to_resultPlayFragment)
    }

    private fun showToastMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), message, duration).show()
    }

    override fun showLoading() {
        binding.loadingLayout.visibility = View.VISIBLE
        binding.errorLayout.visibility = View.GONE
        binding.gameLayout.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.gameLayout.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
    }

    override fun showError(error: Throwable) {
        binding.errorLayout.visibility = View.VISIBLE
        binding.gameLayout.visibility = View.GONE
        binding.loadingLayout.visibility = View.GONE
    }

    override fun toggleSkipButton(visible: Boolean) {
        binding.skipButton.visibility = if (visible) View.VISIBLE else View.GONE
    }
}