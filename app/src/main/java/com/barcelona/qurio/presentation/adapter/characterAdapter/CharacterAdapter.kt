package com.barcelona.qurio.presentation.adapter.characterAdapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barcelona.qurio.presentation.custom_view.CharacterCardView
import com.barcelona.qurio.presentation.model.CharacterGame

class CharacterAdapter(
    private val characters: List<CharacterGame>,
    private val onCharacterClick: (CharacterGame) -> Unit = {}
) : RecyclerView.Adapter<CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val cardView = CharacterCardView(parent.context)
        cardView.layoutParams = ViewGroup.MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(4, 0, 4, 0)
        }
        return CharacterViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(characters[position], onCharacterClick)
    }

    override fun getItemCount(): Int = characters.size
}