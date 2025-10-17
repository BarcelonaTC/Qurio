package com.barcelona.qurio.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.barcelona.qurio.QurioApp
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseDialogFragment
import com.barcelona.qurio.databinding.CharactersSelectionBinding
import com.barcelona.qurio.presentation.adapter.characterAdapter.CharacterAdapter
import com.barcelona.qurio.presentation.model.CharacterGame
import com.barcelona.qurio.presentation.view.CharacterSelectionView
import com.barcelona.qurio.presenter.characterSelection.CharacterSelectionPresenter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import javax.inject.Inject

class CharacterSelectionFragment : BaseDialogFragment<CharactersSelectionBinding>(),
    CharacterSelectionView {

    override val layoutIdDialog: Int = R.layout.characters_selection

    private lateinit var characterAdapter: CharacterAdapter
    private lateinit var currentCharacter: CharacterGame

    @Inject
    lateinit var presenter: CharacterSelectionPresenter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as QurioApp).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(false)
        presenter.attachView(this)
        setupRecyclerView()
        presenter.getSelectedCharacter()
        presenter.loadCharacters()
        binding.confirmButton.setOnClickListener {
            onConfirmButtonClick()
        }
        binding.cancelButton.setOnClickListener {
            onCancelClick()
        }

        parentFragmentManager.setFragmentResult(
            "purchase_success",
            Bundle().apply { putBoolean("refresh_home", true) }
        )
    }

    private fun setupRecyclerView() {
        characterAdapter = CharacterAdapter(emptyList()) { character ->
            onCharacterClick(character)
        }

        binding.charactersRecyclerView.apply {
            layoutManager = FlexboxLayoutManager(context).apply {
                flexDirection = FlexDirection.ROW
                justifyContent = JustifyContent.FLEX_START
                flexWrap = FlexWrap.WRAP
            }
            adapter = characterAdapter
        }
    }


    override fun showCharacters(characters: List<CharacterGame>) {
        characterAdapter.updateCharacters(characters)
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onCancelClick() {
        dismiss()
    }

    override fun onConfirmButtonClick() {
        currentCharacter.let { character ->
            val dialog = CharacterDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt("characterId", character.id)
                }
            }
            dismiss()
            dialog.show(parentFragmentManager, "CharacterDetailFragment")
        }
    }


    override fun onCharacterClick(character: CharacterGame) {
        if (character.isLocked) {
            val dialog = BuyCharacterFragment().apply {
                arguments = Bundle().apply {
                    putInt("characterId", character.id)
                }
            }
            dismiss()
            dialog.show(parentFragmentManager, "BuyCharacterDialog")
            return
        }

        currentCharacter = character
        presenter.onCharacterClicked(character)
        characterAdapter.setSelectedCharacter(character.id)
    }

    override fun showSelectedCharacter(character: CharacterGame) {
        currentCharacter = character
        characterAdapter.setSelectedCharacter(character.id)
    }
}
