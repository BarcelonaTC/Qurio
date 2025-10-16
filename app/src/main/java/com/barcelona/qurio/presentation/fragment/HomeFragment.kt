package com.barcelona.qurio.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.barcelona.qurio.QurioApp
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseFragment
import com.barcelona.qurio.databinding.FragmentHomeBinding
import com.barcelona.qurio.model.dto.gameCards
import com.barcelona.qurio.presentation.adapter.gamecardAdapter.GameCardsAdapter
import com.barcelona.qurio.presentation.adapter.streakAdapter.StreakDayAdapter
import com.barcelona.qurio.presentation.animation.animatePoints
import com.barcelona.qurio.presentation.animation.createGameCardTransformer
import com.barcelona.qurio.presentation.model.CharacterGame
import com.barcelona.qurio.presentation.model.gamecard.GameCardModel
import com.barcelona.qurio.presentation.model.streak.StreakModel
import com.barcelona.qurio.presentation.sounds.SoundPlayerManager
import com.barcelona.qurio.presentation.view.HomeView
import com.barcelona.qurio.presenter.HomePresenter
import jakarta.inject.Inject
import java.text.NumberFormat
import java.util.Locale

class HomeFragment(
) : BaseFragment<FragmentHomeBinding>(), HomeView {
    override val layoutIdFragment: Int = R.layout.fragment_home

    @Inject
    lateinit var presenter: HomePresenter
    private lateinit var soundManager: SoundPlayerManager
    val musicFiles = listOf(R.raw.app_theme_1, R.raw.app_theme_2)
    val selectedMusic = musicFiles.random()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as QurioApp).appComponent.inject(this)
        soundManager = SoundPlayerManager(requireContext())

        presenter.attachView(this)
        setStreak(this.context)
        setupGameCardPager()
        setInteractionListener()
        presenter.updateStreak()
        presenter.getStreak()
        presenter.getTotalPoints()
        presenter.getTotalLives()
        presenter.getTotalRewards()
        presenter.selectedCharacter()
        presenter.getMusicVolumeLevel()
        presenter.getSoundVolumeLevel()
        soundManager.loadSound(R.raw.coins_sound)

        soundManager.loadSound(selectedMusic)
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun onPause() {
        super.onPause()
        soundManager.pauseMusic()
    }

    override fun onResume() {
        super.onResume()
        soundManager.resumeMusic()
    }

    override fun onStop() {
        super.onStop()
        soundManager.pauseMusic()
    }

    override fun onDestroy() {
        presenter.destroyPresenter()
        soundManager.release()
        super.onDestroy()
    }

    private fun setStreak(context: Context?) {
        binding.streakComponent.daysRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupGameCardPager() {
        val adapter = GameCardsAdapter(gameCards, onPlayClick = ::onPlayNowClicked, true)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.recyclerView.offscreenPageLimit = 3

        binding.recyclerView.setPageTransformer(createGameCardTransformer())

        (binding.recyclerView.getChildAt(0) as RecyclerView).overScrollMode =
            RecyclerView.OVER_SCROLL_NEVER
    }

    fun onPlayNowClicked(gameCard: GameCardModel) {
        presenter.checkLivesBeforePlay(
            onHasLives = {
                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToStartPlayFragment(gameCard.categoryId)
                )
            },
            onNoLives = {
                showNoLivesDialog()
            }
        )
    }

    private fun showNoLivesDialog() {
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("لا يوجد محاولات متبقية")
            .setMessage("انتظر حتى تتجدد الأرواح أو اشتر المزيد.")
            .setPositiveButton("حسنًا", null)
            .show()
    }

    private fun setInteractionListener() {
        with(binding) {
            seeAllGames.setOnClickListener {
                findNavController().navigate(R.id.gameFragment)
            }
            seeAllLastGames.setOnClickListener {}
            appBar.profile.setOnClickListener {
                val dialog = CharacterSelectionFragment()
                dialog.show(parentFragmentManager, "CharacterSelectionDialog")
            }
            appBar.settingsIcon.setOnClickListener {
                showSettingsDialog()
            }
            appBar.profile.setOnClickListener {

            }
            settingsDialog.discardButton.setOnClickListener {
                dismissDialog(settingsDialog.root)
            }
            settingsDialog.saveChangesButton.setOnClickListener {
                val soundLevel = binding.settingsDialog.soundSlider.getVolumePercentage()
                val musicLevel = binding.settingsDialog.musicSlider.getVolumePercentage()
                saveVolumeLevels(soundLevel, musicLevel)
                dismissDialog(binding.settingsDialog.root)
            }
            settingsDialog.dialogRoot.setOnDismissListener {
                dismissDialog(settingsDialog.root)
            }
            settingsDialog.soundSlider.setOnVolumeChangeListener { newSoundLevel ->
                val musicLevel = settingsDialog.musicSlider.getVolumePercentage()
                soundManager.setVolumeLevels(newSoundLevel, musicLevel)
            }

            settingsDialog.musicSlider.setOnVolumeChangeListener { newMusicLevel ->
                val soundLevel = settingsDialog.soundSlider.getVolumePercentage()
                soundManager.setVolumeLevels(soundLevel, newMusicLevel)
            }
        }
    }

    private fun showSettingsDialog() {

        binding.settingsDialog.root.alpha = 0f
        binding.settingsDialog.root.visibility = View.VISIBLE
        binding.settingsDialog.dialogRoot.visibility = View.VISIBLE

        showDialog(binding.settingsDialog.root)
        showDialog(binding.settingsDialog.dialogRoot)
    }

    override fun showStreak(streak: StreakModel) {
        binding.streakComponent.streak = streak

        binding.streakComponent.daysRecyclerView.adapter = StreakDayAdapter(streak.days)
    }

    override fun showTotalPoints(totalPoints: Int) {
        soundManager.playSound(R.raw.coins_sound)
        soundManager.playMusic(selectedMusic)

        animatePoints(
            endValue = totalPoints,
            onUpdate = { animatedValue ->
                val formattedValue = NumberFormat.getNumberInstance(Locale.US).format(animatedValue)
                binding.statisticsComponent.pointsCard.pointsAmount.text = formattedValue
            },
        )
    }

    override fun showTotalLives(totalLives: Int) {
        binding.statisticsComponent.livesCard.livesAmount.text = totalLives.toString()
    }

    override fun showTotalRewards(totalRewards: Int) {
        binding.statisticsComponent.awardsCard.awardsAmount.text = totalRewards.toString()
    }

    override fun showSelectedCharacter(selectedCharacter: CharacterGame) {
        binding.appBar.profile.setImageResource(selectedCharacter.imageRes)
        binding.appBar.name.text = selectedCharacter.name
    }

    override fun showMusicVolumeLevel(volumeLevel: Int) {
        binding.settingsDialog.musicSlider.setVolumePercentage(volumeLevel)
        soundManager.setVolumeLevels(
            binding.settingsDialog.soundSlider.getVolumePercentage(),
            volumeLevel
        )
    }

    override fun showSoundVolumeLevel(volumeLevel: Int) {
        binding.settingsDialog.soundSlider.setVolumePercentage(volumeLevel)
        soundManager.setVolumeLevels(
            volumeLevel,
            binding.settingsDialog.musicSlider.getVolumePercentage()
        )
    }

    override fun saveVolumeLevels(soundLevel: Int, musicLevel: Int) {
        presenter.saveVolumeLevels(soundLevel, musicLevel)
    }

    private fun dismissDialog(view: View) {
        view.animate()
            .alpha(0f)
            .setDuration(500)
            .withEndAction { view.visibility = View.GONE }
            .start()
    }

    private fun showDialog(view: View) {
        view.animate()
            .alpha(1f)
            .setDuration(500)
            .start()
    }
}