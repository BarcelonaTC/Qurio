package com.barcelona.qurio.presentation.adapter.characterAdapter

import androidx.recyclerview.widget.RecyclerView
import com.barcelona.qurio.presentation.custom_view.CharacterCardView
import com.barcelona.qurio.presentation.model.CharacterGame

class CharacterViewHolder(
    private val cardView: CharacterCardView
) : RecyclerView.ViewHolder(cardView) {

    fun bind(
        character: CharacterGame,
        isSelected: Boolean,
        onCharacterClick: (CharacterGame) -> Unit
    ) {
        cardView.apply {
            setCharacterName(character.name)
            setCharacterImage(character.imageRes)
            setLocked(isSelected, character.isLocked)
            setSalary(character.price)
            setOnClickListener { onCharacterClick(character) }
        }
    }
}
