package com.barcelona.qurio.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.barcelona.qurio.QurioApp
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseFragment
import com.barcelona.qurio.databinding.FragmentStartPlayBinding
import com.barcelona.qurio.databinding.NoInternetScreenBinding
import com.barcelona.qurio.presentation.adapter.QuestionAdapter
import com.barcelona.qurio.presentation.model.Question
import com.barcelona.qurio.presentation.model.TriviaGameSession
import com.barcelona.qurio.presentation.sounds.SoundPlayerManager
import com.barcelona.qurio.presentation.view.StartPlayView
import com.barcelona.qurio.presenter.StartPlayPresenter
import javax.inject.Inject
@RequiresApi(Build.VERSION_CODES.O)

class StartPlayFragment : BaseFragment<FragmentStartPlayBinding>(), StartPlayView {

    override val layoutIdFragment: Int get() = R.layout.fragment_start_play
    private lateinit var errorBinding: NoInternetScreenBinding

    @Inject
    lateinit var startPlayPresenter: StartPlayPresenter

    private lateinit var soundManager: SoundPlayerManager
    val musicFiles = listOf(R.raw.app_theme_1, R.raw.app_theme_2)
    val selectedMusic = musicFiles.random()

    private var adapter: QuestionAdapter? = null
    private val args: StartPlayFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity().application as QurioApp).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)
        soundManager = (requireActivity().application as QurioApp).soundPlayerManager
        startPlayPresenter.attachView(this)
        errorBinding = NoInternetScreenBinding.bind(binding.errorLayout)
        startPlayPresenter.getQuestions(args.categoryId, args.categoryName)
        startPlayPresenter.getTotalLives()
        startPlayPresenter.getMusicVolumeLevel()
        startPlayPresenter.getSoundVolumeLevel()
        soundManager.loadSound(R.raw.timer)
        soundManager.loadSound(R.raw.correct)
        soundManager.loadSound(R.raw.wrong)
        setupListeners()
    }

    private fun setupListeners() {
        binding.checkButton.setOnClickListener {
            startPlayPresenter.onCheckButtonClicked(adapter?.selectedPosition)
        }

        binding.skipButton.setOnClickListener {
            startPlayPresenter.nextQuestion()
        }
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        errorBinding.retryButton.setOnClickListener {
            startPlayPresenter.getQuestions(args.categoryId, args.categoryName)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        startPlayPresenter.detachView()
        startPlayPresenter.destroyTimer()
    }

    override fun onResume() {
        super.onResume()
        soundManager.resumeMusic()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
        soundManager.stopMusic()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundManager.stopMusic()
        soundManager.playMusic(selectedMusic)
    }

    override fun showQuestions(questions: List<Question>) {
        binding.gameLayout.visibility = View.VISIBLE
    }

    override fun showQuestion(
        question: Question,
        questionNumber: String
    ) {
        soundManager.stopMusic()
        soundManager.playMusic(R.raw.timer)
        val decodedText =
            Html.fromHtml(question.question, Html.FROM_HTML_MODE_LEGACY).toString()
        binding.questionsPager.setQuestion(decodedText)
        binding.questionsPager.setQuestionNumber(questionNumber)
    }

    override fun showAnswers(answers: List<String>) {
        adapter = QuestionAdapter(answers) {}
        binding.answersLayout.adapter = adapter
    }

    override fun highlightAnswers(correctAnswer: String, selectedPosition: Int) {
        soundManager.stopMusic()
        val isCorrect = adapter?.answers?.getOrNull(selectedPosition) == correctAnswer
        if (isCorrect) {
            soundManager.playSound(R.raw.correct)
        } else {
            soundManager.playSound(R.raw.wrong)
        }
        binding.scoreIndicator.showScore(isCorrect)
        adapter?.correctAnswer = correctAnswer
        adapter?.showCorrect = true
        adapter?.notifyDataSetChanged()
        binding.checkButton.setText(requireContext().getString(R.string.next))
        binding.skipButton.visibility = View.GONE
    }

    override fun resetAnswers() {
        binding.checkButton.setText(requireContext().getString(R.string.check))
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
    }

    override fun onGameSessionSaved(session: TriviaGameSession) {
        val action = StartPlayFragmentDirections
            .actionStartPlayFragmentToResultPlayFragment(session, args.categoryId, args.categoryName)
        findNavController().navigate(action)
    }

    override fun showTotalLives(lives: Int) {
        binding.totalLives.text = lives.toString()
    }

    override fun getMusicVolumeLevel(volumeLevel: Int) {
        val currentSoundLevel = soundManager.getCurrentSoundVolume()
        soundManager.setVolumeLevels(currentSoundLevel, volumeLevel)
    }

    override fun getSoundVolumeLevel(volumeLevel: Int) {
        val currentMusicLevel = soundManager.getCurrentMusicVolume()
        soundManager.setVolumeLevels(volumeLevel, currentMusicLevel)
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
    }

    override fun showError(error: Throwable) {

        binding.gameLayout.visibility = View.GONE
        binding.loadingLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
    }

    override fun toggleSkipButton(visible: Boolean) {
        binding.skipButton.visibility = if (visible) View.VISIBLE else View.GONE
    }
}