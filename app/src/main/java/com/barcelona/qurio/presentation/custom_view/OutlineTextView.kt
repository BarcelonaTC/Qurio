package com.barcelona.qurio.presentation.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.barcelona.qurio.R

class OutlineTextView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    private val outlinePaint = Paint()
    private var outlineColor: Int = Color.TRANSPARENT
    private var outlineWidth: Float = 0f

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.OutlineTextView,
            0, 0
        ).apply {
            try {
                outlineColor = getColor(R.styleable.OutlineTextView_outlineColor, Color.TRANSPARENT)
                outlineWidth = getDimension(R.styleable.OutlineTextView_outlineWidth, 0f)
            } finally {
                recycle()
            }
        }

        outlinePaint.isAntiAlias = true
        outlinePaint.style = Paint.Style.STROKE
        outlinePaint.strokeWidth = outlineWidth
        outlinePaint.color = outlineColor

        val paddingIncrease = outlineWidth.toInt()
        setPadding(
            paddingLeft + paddingIncrease,
            paddingTop + paddingIncrease,
            paddingRight + paddingIncrease,
            paddingBottom + paddingIncrease
        )
    }

    override fun onDraw(canvas: Canvas) {
        val textStr = text?.toString() ?: return

        outlinePaint.textSize = textSize
        outlinePaint.typeface = typeface

        val baseX = paddingLeft.toFloat()
        val baseY = baseline.toFloat()

        canvas.drawText(textStr, baseX, baseY, outlinePaint)
        super.onDraw(canvas)
    }
}