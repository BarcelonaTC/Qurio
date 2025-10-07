package com.barcelona.qurio.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import com.barcelona.qurio.R

class PlayButtonView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyle: Int = 0
) : View(context, attrs, defStyle) {

    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f
        color = context.getColor(R.color.primary)
    }

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = context.getColor(R.color.primary)
    }

    private val mainGradientPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val leftTriangleGradient = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rightTriangleGradient = Paint(Paint.ANTI_ALIAS_FLAG)

    private val bgPath = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        buildPath(w, h)
        buildGradients(w, h)
    }

    private fun buildPath(w: Int, h: Int) {

        val totalWidth = w.toFloat()
        val rectWidth = totalWidth * rectWidthRatio
        val triWidth = totalWidth * triangleWidthRatio
        val leftRectStart = triWidth
        val rightRectEnd = leftRectStart + rectWidth

        bgPath.reset()
        bgPath.moveTo(0f, h / 2f)
        bgPath.lineTo(leftRectStart, 0f)
        bgPath.lineTo(rightRectEnd, 0f)
        bgPath.lineTo(totalWidth, h / 2f)
        bgPath.lineTo(rightRectEnd, h.toFloat())
        bgPath.lineTo(leftRectStart, h.toFloat())
        bgPath.close()
    }

    private fun buildGradients(w: Int, h: Int) {
        mainGradientPaint.shader = LinearGradient(
            0f, h * 0.65f, 0f, h.toFloat(),
            Color.TRANSPARENT,
            Color.argb(120, 255, 255, 255),
            Shader.TileMode.CLAMP
        )
        leftTriangleGradient.shader = LinearGradient(
            0f, h.toFloat(),
            w * 0.18f, h * 0.7f,
            Color.argb(90, 255, 255, 255),
            Color.TRANSPARENT,
            Shader.TileMode.CLAMP
        )
        rightTriangleGradient.shader = LinearGradient(
            w.toFloat(), h.toFloat(),
            w * 0.82f, h * 0.7f,
            Color.argb(90, 255, 255, 255),
            Color.TRANSPARENT,
            Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(bgPath, fillPaint)
        canvas.drawPath(bgPath, mainGradientPaint)

        drawLeftTriangle(canvas)
        drawRightTriangle(canvas)

        canvas.drawPath(bgPath, borderPaint)
    }

    private fun drawLeftTriangle(canvas: Canvas) {
        val leftTriangle = Path().apply {
            moveTo(0f, height / 2f)
            lineTo(width * (31f / 168f), 0f)
            lineTo(width * (31f / 168f), height.toFloat())
            close()
        }
        canvas.drawPath(leftTriangle, leftTriangleGradient)
    }

    private fun drawRightTriangle(canvas: Canvas) {
        val rightTriangle = Path().apply {
            moveTo(width.toFloat(), height / 2f)
            lineTo(width * (137f / 168f), 0f)
            lineTo(width * (137f / 168f), height.toFloat())
            close()
        }
        canvas.drawPath(rightTriangle, rightTriangleGradient)
    }

    private companion object {
        const val rectWidthRatio = 0.631f
        const val triangleWidthRatio = 0.185f
    }
}