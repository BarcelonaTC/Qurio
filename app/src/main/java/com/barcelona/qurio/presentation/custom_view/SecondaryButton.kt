package com.barcelona.qurio.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.barcelona.qurio.R
import com.barcelona.qurio.databinding.SecondaryButtonBinding


class SecondaryButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: SecondaryButtonBinding =
        SecondaryButtonBinding.inflate(LayoutInflater.from(context), this)
    private var isCustomEnabled = true

    init {
        orientation = VERTICAL

        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SecondaryButton)
            try {
                isCustomEnabled = typedArray.getBoolean(
                    R.styleable.SecondaryButton_secondaryButtonEnabled,
                    true
                )

                val textValue = typedArray.getString(R.styleable.SecondaryButton_secondaryText)
                    ?: context.getString(R.string.submit)

                binding.textViewLabel.text = textValue
                setEnabledState(isCustomEnabled)
            } finally {
                typedArray.recycle()
            }
        }
    }

    private fun setEnabledState(enabled: Boolean) {
        binding.rootLayout.isEnabled = enabled
        binding.textViewLabel.isEnabled = enabled
        binding.rootLayout.background =
            ContextCompat.getDrawable(context, R.drawable.secondary_button_bg)
        binding.textViewLabel.setTextColor(
            ContextCompat.getColorStateList(context, R.color.text_button_color)
        )
    }

    fun setButtonEnabled(enabled: Boolean) {
        isCustomEnabled = enabled
        setEnabledState(enabled)
    }

    fun setText(text: String) {
        binding.textViewLabel.text = text
    }

    fun getText(): String = binding.textViewLabel.text.toString()
}