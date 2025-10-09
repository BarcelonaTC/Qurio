package com.barcelona.qurio.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.barcelona.qurio.R
import com.barcelona.qurio.databinding.TextButtonBinding

class TextButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: TextButtonBinding =
        TextButtonBinding.inflate(LayoutInflater.from(context), this)

    private var isCustomEnabled = true

    init {
        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextButton)
            try {
                isCustomEnabled = typedArray.getBoolean(
                    R.styleable.TextButton_textButtonEnabled,
                    true
                )

                val textAttr =
                    context.obtainStyledAttributes(attrs, intArrayOf(android.R.attr.text))
                val textResId = textAttr.getResourceId(0, 0)
                val textValue = if (textResId != 0) {
                    context.getString(textResId)
                } else {
                    textAttr.getString(0) ?: context.getString(R.string.submit)
                }
                textAttr.recycle()

                setEnabledState(isCustomEnabled)
                binding.textLabel.text = textValue
            } finally {
                typedArray.recycle()
            }
        }
    }

    private fun setEnabledState(enabled: Boolean) {
        binding.textLabel.isEnabled = enabled
        binding.textLabel.setTextColor(
            ContextCompat.getColorStateList(context, R.color.text_button_color)
        )
    }

    fun setTextButtonEnabled(enabled: Boolean) {
        isCustomEnabled = enabled
        setEnabledState(enabled)
    }

    fun setText(text: String) {
        binding.textLabel.text = text
    }

    fun getText(): String = binding.textLabel.text.toString()
}