package com.barcelona.qurio.presentation.adapter.bindingAdapter

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.barcelona.qurio.customView.GameCardCustomView
import com.barcelona.qurio.presentation.model.gamecard.GameCard

@BindingAdapter("app:bindCardData")
fun bindGameCardData(view: GameCardCustomView, data: GameCard) {

    val colorResId = data.cardBorderColor
    val actualColor = ContextCompat.getColor(view.context, colorResId)
    view.setCardBorderColor(actualColor)

    val gradientColorResId = data.cardBottomGradient
    val gradientDrawable = ContextCompat.getDrawable(view.context, gradientColorResId)
    view.setCardBottomGradientDrawable(gradientDrawable)

    val strokeWidth=data.cardBorderWidth
    view.setCardBorderWidth(strokeWidth.toFloat())

    val imageResId = data.cardImage
    view.setCardSrc(imageResId)


}
