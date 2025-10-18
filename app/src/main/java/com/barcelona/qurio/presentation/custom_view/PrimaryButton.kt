package com.barcelona.qurio.presentation.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.barcelona.qurio.R
import com.barcelona.qurio.databinding.PrimaryButtonBinding

class PrimaryButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: PrimaryButtonBinding
    private var isCustomEnabled = true
    private val gradientPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var onPrimaryColor = ContextCompat.getColor(context, R.color.on_primary)
    private val startColor = ColorUtils.setAlphaComponent(onPrimaryColor, 130)

    init {
        orientation = VERTICAL
        setWillNotDraw(false)

        val inflater = LayoutInflater.from(context)
        binding = PrimaryButtonBinding.inflate(inflater, this)

        if (attrs != null) {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PrimaryButton)
            try {
                isCustomEnabled = typedArray.getBoolean(
                    R.styleable.PrimaryButton_primaryButtonEnabled,
                    true
                )

                val textValue = typedArray.getString(R.styleable.PrimaryButton_primaryText)
                    ?: context.getString(R.string.submit)
                binding.textViewLabel.text = textValue
                setButtonEnabled(isCustomEnabled)

                val iconResId = typedArray.getResourceId(R.styleable.PrimaryButton_iconEnd, 0)
                if (iconResId != 0) {
                    binding.iconView.setImageResource(iconResId)
                    binding.iconView.visibility = VISIBLE
                } else {
                    binding.iconView.visibility = GONE
                }

            } finally {
                typedArray.recycle()
            }
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        if (!isCustomEnabled) return

        val gradientHeight = height * 0.30f
        val radius = 16f * resources.displayMetrics.density

        val gradient = LinearGradient(
            0f,
            height - gradientHeight,
            0f,
            height.toFloat(),
            Color.TRANSPARENT,
            startColor,
            Shader.TileMode.CLAMP
        )
        gradientPaint.shader = gradient

        val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        val path = Path()
        path.addRoundRect(rect, radius, radius, Path.Direction.CW)

        canvas.save()
        canvas.clipPath(path)
        canvas.drawRect(
            0f,
            height - gradientHeight,
            width.toFloat(),
            height.toFloat(),
            gradientPaint
        )
        canvas.restore()
    }

    fun setButtonEnabled(enabled: Boolean) {
        isEnabled = enabled
        isCustomEnabled = enabled
        binding.rootLayout.isEnabled = enabled
        binding.textViewLabel.isEnabled = enabled
        binding.rootLayout.background =
            ContextCompat.getDrawable(context, R.drawable.primary_button_bg)
        invalidate()
    }

    fun setText(text: String) {
        binding.textViewLabel.text = text
    }

    fun getText(): String = binding.textViewLabel.text.toString()
}
