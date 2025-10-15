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
import com.barcelona.qurio.presentation.model.gamecard.GameCardModel
import com.barcelona.qurio.presentation.model.streak.StreakModel
import com.barcelona.qurio.presentation.sounds.CoinSoundPlayer
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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as QurioApp).appComponent.inject(this)
        presenter.attachView(this)
        setStreak(this.context)
        setupGameCardPager()
        setInteractionListener()
        presenter.updateStreak()
        presenter.getStreak()
        presenter.getTotalPoints()
        presenter.getTotalLives()

    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }

    override fun onDestroy() {
        presenter.destroyPresenter()
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
            seeAllLastGames.setOnClickListener {
            }
            binding.appBar.profile.setOnClickListener {
                findNavController().navigate(R.id.characterSelectionFragment)
            }
        }
    }

    override fun showStreak(streak: StreakModel) {
        binding.streakComponent.streak = streak

        binding.streakComponent.daysRecyclerView.adapter = StreakDayAdapter(streak.days)
    }

    override fun showTotalPoints(totalPoints: Int) {
        val soundPlayer = CoinSoundPlayer(context)
        soundPlayer.loadSound(R.raw.coins_sound) {
            animatePoints(
                endValue = totalPoints,
                onUpdate = { animatedValue ->
                    val formattedValue =
                        NumberFormat.getNumberInstance(Locale.US).format(animatedValue)
                    binding.statisticsComponent.pointsCard.pointsAmount.text = formattedValue
                },
                onEnd = {
                    soundPlayer.release()
                }
            )
            soundPlayer.play()
        }
    }

    override fun showTotalLives(totalLives: Int) {
        binding.statisticsComponent.livesCard.livesAmount.text = totalLives.toString()
    }
}