package com.barcelona.qurio.presentation.fragment

import android.os.Bundle
import android.view.View
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseDialogFragment
import com.barcelona.qurio.databinding.DifficultyLevelDialogBinding
import com.barcelona.qurio.presentation.model.LevelType
import com.barcelona.qurio.presentation.view.DifficultyLevelView

class DifficultyLevelFragment : BaseDialogFragment<DifficultyLevelDialogBinding>(),
    DifficultyLevelView {
    override val layoutIdDialog: Int
        get() = R.layout.difficulty_level_dialog
    private lateinit var levelType: LevelType

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmButton.setOnClickListener {
            onConfirmClick(levelType)
        }


        binding.cancelButton.setOnClickListener {
            onCancelClick()
        }
        binding.chipLayout.easyChip.setOnClickListener {
            onEasyClick()
            setConfirmButtonEnabled(true)
        }
        binding.chipLayout.mediumChip.setOnClickListener {
            onMediumClick()
            setConfirmButtonEnabled(true)
        }
        binding.chipLayout.hardChip.setOnClickListener {
            onHardClick()
            setConfirmButtonEnabled(true)
        }
    }

    override fun onConfirmClick(levelType: LevelType) {
        if (binding.confirmButton.isEnabled) {
            parentFragmentManager.setFragmentResult("level_game_type", Bundle().apply {
                putString("levelType", levelType.name.lowercase())
            })

            dismiss()
        }
    }

    override fun onEasyClick() {
        levelType = LevelType.EASY
        binding.chipLayout.easyChip.isChecked = true
        binding.chipLayout.mediumChip.isChecked = false
        binding.chipLayout.hardChip.isChecked = false
        binding.confirmButton.isEnabled = true
    }

    override fun onMediumClick() {
        levelType = LevelType.MEDIUM
        binding.chipLayout.mediumChip.isChecked = true
        binding.chipLayout.hardChip.isChecked = false
        binding.chipLayout.easyChip.isChecked = false
        binding.confirmButton.isEnabled = true
    }

    override fun onHardClick() {
        levelType = LevelType.HARD
        binding.chipLayout.hardChip.isChecked = true
        binding.chipLayout.mediumChip.isChecked = false
        binding.chipLayout.easyChip.isChecked = false
        binding.confirmButton.isEnabled = true
    }

    override fun setConfirmButtonEnabled(enable: Boolean) {
        binding.confirmButton.setButtonEnabled(enable)
    }


    override fun onCancelClick() {
        dismiss()
    }

}