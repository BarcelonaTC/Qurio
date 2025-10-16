package com.barcelona.qurio.presentation.adapter.lastGame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barcelona.qurio.databinding.ItemLastGameBinding
import com.barcelona.qurio.presentation.model.LastGame

class LastGameAdapter(
    private val lastGames: List<LastGame>
) : RecyclerView.Adapter<LastGameAdapter.LastGameViewHolder>() {
    inner class LastGameViewHolder(val binding: ItemLastGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(game: LastGame) = with(binding) {
            binding.lastgame = game
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastGameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLastGameBinding.inflate(inflater, parent, false)
        return LastGameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LastGameViewHolder, position: Int) {
        val game = lastGames[position]
        holder.bind(game)
    }

    override fun getItemCount(): Int = lastGames.size
}