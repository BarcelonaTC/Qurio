package com.barcelona.qurio.presentation.adapter.gamecardAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barcelona.qurio.databinding.GameCardItemBinding
import com.barcelona.qurio.presentation.model.gamecard.GameCard

class GameCardAdapter(private val gameCard: List<GameCard>): RecyclerView.Adapter<GameCardAdapter.GameCardViewHolder>(){

    class GameCardViewHolder(val view: GameCardItemBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(gameCard: GameCard){
            view.gameCardData = gameCard
            view.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameCardViewHolder {
        val viewBinding = GameCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameCardViewHolder(viewBinding)
    }

    override fun onBindViewHolder(
        holder: GameCardViewHolder,
        position: Int
    ) {
        val gameCard = gameCard[position]
        holder.bind(gameCard)
    }

    override fun getItemCount(): Int {
        return gameCard.size
    }

}