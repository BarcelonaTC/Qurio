package com.barcelona.qurio.presentation.adapter.bindingAdapter

import androidx.databinding.BindingAdapter
import carbon.widget.FrameLayout
import com.barcelona.qurio.R

@BindingAdapter("achieved")
fun setDayBackground(view: FrameLayout, achieved: Boolean) {
    val context = view.context
    view.setBackgroundColor(
        if (achieved) context.getColor(R.color.orange_variant)
        else context.getColor(R.color.surface)
    )
    view.setCornerRadius(500f)
    view.elevation = if (achieved) 20f else 0f
    view.setElevationShadowColor(
        if (achieved) context.getColor(R.color.fire_shadow)
        else context.getColor(R.color.transparent)
    )
}