package com.barcelona.qurio.presentation.view

import com.barcelona.qurio.base.BaseView

interface BuyLifeView : BaseView {
    fun onBuyClick()
    fun onCancelClick()
    fun setBuyButtonEnabled(enable: Boolean)
}