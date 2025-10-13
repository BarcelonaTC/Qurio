package com.barcelona.qurio.presentation.fragment

import android.os.Bundle
import android.view.View
import com.barcelona.qurio.QurioApp
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseFragment
import com.barcelona.qurio.databinding.CharacterInfoDialogBinding
import com.barcelona.qurio.presentation.model.CharacterGame
import com.barcelona.qurio.presentation.view.CharacterDetailView
import com.barcelona.qurio.presenter.characterSelection.CharacterDetailPresenter
import javax.inject.Inject

class CharacterDetailFragment : BaseFragment<CharacterInfoDialogBinding>(),
    CharacterDetailView {

    override val layoutIdFragment: Int
        get() = R.layout.character_info_dialog

    @Inject
    lateinit var presenter: CharacterDetailPresenter

    private var characterId: Int = -1

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        (requireActivity().application as QurioApp).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        characterId = arguments?.getInt("characterId") ?: -1
        if (characterId != -1) {
            presenter.attachView(this)
            presenter.loadCharacter(characterId)
        }
    }

    override fun showCharacter(character: CharacterGame) {
        binding.characterName.text = character.name
        binding.characterAge.text = "Age: ${character.age}"
        binding.characterDescription.text = character.description
        binding.characterImage.setImageResource(character.imageRes)
    }
}
