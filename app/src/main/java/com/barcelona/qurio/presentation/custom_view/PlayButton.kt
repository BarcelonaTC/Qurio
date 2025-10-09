package com.barcelona.qurio.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.barcelona.qurio.R
import com.barcelona.qurio.databinding.PlayButtonBinding

class PlayButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: PlayButtonBinding =
        PlayButtonBinding.inflate(LayoutInflater.from(context), this)

    init {
        isClickable = true
        isFocusable = true

        if (attrs != null) {
            val ta = context.obtainStyledAttributes(attrs, intArrayOf(android.R.attr.text))
            try {
                val textResId = ta.getResourceId(0, 0)
                val textValue = if (textResId != 0) {
                    context.getString(textResId)
                } else {
                    ta.getString(0) ?: context.getString(R.string.play_now)
                }
                binding.textView.text = textValue
            } finally {
                ta.recycle()
            }
        }
    }

    fun setText(text: String) {
        binding.textView.text = text
    }

    fun getText(): String = binding.textView.text.toString()
}