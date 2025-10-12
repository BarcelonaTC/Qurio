package com.barcelona.qurio.presentation.adapter.gamecardAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barcelona.qurio.R
import com.barcelona.qurio.databinding.GameCardItemBinding
import com.barcelona.qurio.presentation.model.gamecard.GameCardModel

class GameCardsAdapter(
    private val gameCard: List<GameCardModel>,
    private val onPlayClick: (GameCardModel) -> Unit,
    private val matchParentHeight: Boolean = false
) : RecyclerView.Adapter<GameCardsAdapter.GameCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameCardViewHolder {
        val binding = GameCardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GameCardViewHolder(binding, onPlayClick)
    }

    override fun onBindViewHolder(holder: GameCardViewHolder, position: Int) {
        holder.bind(gameCard[position], matchParentHeight)
    }

    override fun getItemCount(): Int = gameCard.size

    class GameCardViewHolder(
        private val binding: GameCardItemBinding,
        private val onPlayClick: (GameCardModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val context: Context = binding.root.context

        fun bind(gameCard: GameCardModel, matchParentHeight: Boolean) {
            binding.gameCardData = gameCard

            configureRootHeight(matchParentHeight)

            if (!matchParentHeight) {
                configureFixedHeightLayout()
            }

            binding.playButton.containerPlayButton.setOnClickListener {
                onPlayClick(gameCard)
            }

            binding.executePendingBindings()
        }

        private fun configureRootHeight(matchParentHeight: Boolean) {
            binding.root.layoutParams = binding.root.layoutParams.apply {
                height = if (matchParentHeight) {
                    ViewGroup.LayoutParams.MATCH_PARENT
                } else {
                    context.resources.getDimensionPixelSize(R.dimen.list_game_card_height)
                }
            }
        }

        private fun configureFixedHeightLayout() {
            binding.gameImage.layoutParams = binding.gameImage.layoutParams.apply {
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }

            (binding.root.layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(
                0, 0, 0,
                context.resources.getDimensionPixelSize(R.dimen.game_card_spacing)
            )

            binding.playButton.root.layoutParams = binding.playButton.root.layoutParams.apply {
                height = context.resources.getDimensionPixelSize(R.dimen.play_game_height)
                width = context.resources.getDimensionPixelSize(R.dimen.play_game_width)
            }

            binding.textTitle.setTextAppearance(R.style.Title_Small)
        }
    }
}