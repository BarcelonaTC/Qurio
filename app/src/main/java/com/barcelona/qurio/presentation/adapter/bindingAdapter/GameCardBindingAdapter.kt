package com.barcelona.qurio.presentation.adapter.bindingAdapter

import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.barcelona.qurio.presentation.custom_view.GameCard
import com.barcelona.qurio.presentation.model.gamecard.GameCardModel

@BindingAdapter("app:bindCardData")
fun bindGameCardData(view: GameCard, data: GameCardModel) {

    val colorResId = data.borderColor
    val actualColor = ContextCompat.getColor(view.context, colorResId)
    view.setCardBorderColor(actualColor)

    val gradientColorResId = data.bottomGradient
    val gradientDrawable = ContextCompat.getDrawable(view.context, gradientColorResId)
    view.setCardBottomGradientDrawable(gradientDrawable)

    val strokeWidth=data.borderWidth
    view.setCardBorderWidth(strokeWidth.toFloat())

    val imageResId = data.image
    view.setCardSrc(imageResId)


}
