package com.barcelona.qurio.presentation.adapter.characterAdapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.barcelona.qurio.presentation.custom_view.CharacterCardView
import com.barcelona.qurio.presentation.model.CharacterGame

class CharacterViewHolder(
    private val cardView: CharacterCardView
) : RecyclerView.ViewHolder(cardView) {

    fun bind(character: CharacterGame, onCharacterClick: (CharacterGame) -> Unit) {
        Log.d(
            "CharacterViewHolder",
            "Binding character: ${character.name}, locked: ${character.isLocked}"
        )

        cardView.apply {
            setCharacterName(character.name)
            Log.d("CharacterViewHolder", "Set character name: ${character.name}")

            setCharacterImage(character.imageRes)

            setLocked(character.isSelected, character.isLocked)

            setSalary(character.price)

            setOnClickListener {
                onCharacterClick(character)
            }
        }
    }
}