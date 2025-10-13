package com.barcelona.qurio.presentation.adapter.characterAdapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.barcelona.qurio.presentation.custom_view.CharacterCardView
import com.barcelona.qurio.presentation.model.CharacterGame
import com.google.android.flexbox.FlexboxLayoutManager

@SuppressLint("NotifyDataSetChanged")

class CharacterAdapter(
    private var characters: List<CharacterGame>,
    private val onCharacterClick: (CharacterGame) -> Unit
) : RecyclerView.Adapter<CharacterViewHolder>() {

    private var selectedCharacterId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val cardView = CharacterCardView(parent.context)
        val params = FlexboxLayoutManager.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        cardView.layoutParams = params
        return CharacterViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        val isSelected = character.id == selectedCharacterId

        holder.bind(character, isSelected, onCharacterClick)
    }

    override fun getItemCount(): Int = characters.size

    fun updateCharacters(newCharacters: List<CharacterGame>) {
        characters = newCharacters
        selectedCharacterId = newCharacters.find { it.isSelected }?.id
        notifyDataSetChanged()
    }

    fun setSelectedCharacter(characterId: Int) {
        selectedCharacterId = characterId
        notifyDataSetChanged()
    }
}
