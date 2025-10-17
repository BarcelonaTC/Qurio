package com.barcelona.qurio.presentation.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.barcelona.qurio.QurioApp
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseDialogFragment
import com.barcelona.qurio.databinding.BuyCharacterDialogBinding
import com.barcelona.qurio.presentation.model.CharacterGame
import com.barcelona.qurio.presentation.view.BuyCharacterView
import com.barcelona.qurio.presenter.characterSelection.BuyCharacterPresenter
import javax.inject.Inject

class BuyCharacterFragment : BaseDialogFragment<BuyCharacterDialogBinding>(), BuyCharacterView {
    override val layoutIdDialog: Int
        get() = R.layout.buy_character_dialog

    @Inject
    lateinit var presenter: BuyCharacterPresenter

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
        binding.buyConfirmButton.setOnClickListener {
            onBuyClick(characterId)
        }

        binding.buyCancelButton.setOnClickListener {
            onCancelClick()
        }
    }

    override fun showCharacter(character: CharacterGame) {
        binding.characterPrice.text = character.price.toString()
        binding.characterImage.setImageResource(character.lockedImage)
    }

    override fun setBuyButtonEnabled(enable: Boolean) {
        binding.buyConfirmButton.setButtonEnabled(enable)
    }

    override fun onCancelClick() {
        dismiss()
    }

    override fun onBuyClick(characterId: Int) {
        if (binding.buyConfirmButton.isEnabled) {
            presenter.buyCharacter(characterId)
        }
    }

    override fun onCharacterBought(characterId: Int) {
        parentFragmentManager.setFragmentResult(
            "character_bought",
            Bundle().apply { putInt("characterId", characterId) }
        )
        dismiss()
    }

    override fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
