// CharacterSelectionFragment.kt
package com.barcelona.qurio.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseFragment
import com.barcelona.qurio.databinding.CharactersSelectionBinding
import com.barcelona.qurio.presentation.adapter.characterAdapter.CharacterAdapter
import com.barcelona.qurio.presentation.model.CharacterGame

class CharacterSelectionFragment : BaseFragment<CharactersSelectionBinding>() {

    override val layoutIdFragment: Int = R.layout.characters_selection

    private lateinit var characterAdapter: CharacterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(false)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val characters = listOf(
            CharacterGame("Kaiyo", R.drawable.rika, true, 300, false),
            CharacterGame("Kaiyo", R.drawable.character_kaiyo_open, true, 300, false),
            CharacterGame("Mimi", R.drawable.mimi_character, false, 700, false),
            CharacterGame("Yoru", R.drawable.yoru_character, true, 1000, false),
            CharacterGame("Kuro", R.drawable.kuro_character, true, 3000, true),
            CharacterGame("Miko", R.drawable.miko_character, true, 7000, false),
            CharacterGame("Aori", R.drawable.aori_character, true, 12000, false),
            CharacterGame("Nara", R.drawable.nara_character, true, 30000, false),
            CharacterGame("Renji", R.drawable.renji_character, true, 50000, false)
        )

        characterAdapter = CharacterAdapter(characters) { character ->
            onCharacterClicked(character)
        }

        binding.charactersRecyclerView.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = characterAdapter
        }
    }

    private fun onCharacterClicked(character: CharacterGame) {
        if (character.isLocked) {
            Toast.makeText(context, "${character.name} is locked!", Toast.LENGTH_SHORT).show()
        } else {
            character.isSelected = true
            Toast.makeText(context, "Selected ${character.name}", Toast.LENGTH_SHORT).show()
        }
    }
}