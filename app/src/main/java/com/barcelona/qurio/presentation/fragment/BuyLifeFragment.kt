package com.barcelona.qurio.presentation.fragment

import android.os.Bundle
import android.view.View
import com.barcelona.qurio.QurioApp
import com.barcelona.qurio.R
import com.barcelona.qurio.base.BaseDialogFragment
import com.barcelona.qurio.databinding.BuyLifeLayoutBinding
import com.barcelona.qurio.presentation.view.BuyLifeView
import com.barcelona.qurio.presenter.characterSelection.BuyLifePresenter
import javax.inject.Inject

class BuyLifeFragment : BaseDialogFragment<BuyLifeLayoutBinding>(), BuyLifeView {
    override val layoutIdDialog: Int
        get() = R.layout.buy_life_layout

    @Inject
    lateinit var presenter: BuyLifePresenter

    override fun onAttach(context: android.content.Context) {
        super.onAttach(context)
        (requireActivity().application as QurioApp).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.buyButtonEnable()

        binding.buttonBuy.setOnClickListener {
            onBuyClick()
        }

        binding.buttonCancel.setOnClickListener {
            onCancelClick()
        }
    }


    override fun onBuyClick() {
        if (binding.buttonBuy.isEnabled) {
            presenter.onBuyClick()
            dismiss()
        }
    }

    override fun onCancelClick() {
        dismiss()
    }

    override fun setBuyButtonEnabled(enable: Boolean) {
        binding.buttonBuy.setButtonEnabled(enable)
    }
}