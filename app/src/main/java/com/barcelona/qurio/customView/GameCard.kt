package com.barcelona.qurio.customView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.createBitmap
import kotlin.math.max

class GameCard(
    context: Context,
    attrs: AttributeSet
) : View(context, attrs) {

    private var imageBitmap: Bitmap? = null
    private var gradientDrawable: Drawable? = null
    private val path = Path()
    private val imagePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val matrix = Matrix()

    fun setCardBorderWidth(px: Float) {
        val strokeWidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            px,
            resources.displayMetrics
        )
        strokePaint.strokeWidth = strokeWidth
        invalidate()
    }

    fun setCardSrc(imageResId: Int) {
        setImageDrawable(imageResId)
        setupShader()
        invalidate()
    }

    fun setCardBottomGradientDrawable(drawable: Drawable?) {
        gradientDrawable = drawable
        invalidate()
    }

    fun setCardBorderColor(color: Int) {
        strokePaint.color = color
        invalidate()
    }

    private fun setImageDrawable(resId: Int) {
        val drawable = ContextCompat.getDrawable(context, resId) ?: return
        imageBitmap = if (drawable is BitmapDrawable) {
            drawable.bitmap
        } else {
            createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight).apply {
                val canvas = Canvas(this)
                drawable.setBounds(0, 0, canvas.width, canvas.height)
                drawable.draw(canvas)
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setupPath(w.toFloat(), h.toFloat())
        setupShader()
    }

    private fun setupPath(w: Float, h: Float) {
        val topCut = 70f
        val bottomCut = 40f
        val inset = w * 0.05f
        path.apply {
            reset()
            moveTo(topCut, 0f)
            lineTo(w - topCut, 0f)
            lineTo(w, topCut)
            lineTo(w - inset, h - bottomCut)
            lineTo(w - inset - bottomCut, h)
            lineTo(inset + bottomCut, h)
            lineTo(inset, h - bottomCut)
            lineTo(0f, topCut)
            close()
        }
    }

    private fun setupShader() {
        imageBitmap?.let { bitmap ->
            val shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            val vw = width.toFloat()
            val vh = height.toFloat()
            val bw = bitmap.width.toFloat()
            val bh = bitmap.height.toFloat()

            matrix.reset()
            val scale = max(vw / bw, vh / bh)
            matrix.setScale(scale, scale)
            matrix.postTranslate((vw - bw * scale) / 2, (vh - bh * scale) / 2)

            shader.setLocalMatrix(matrix)
            imagePaint.shader = shader
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.clipPath(path)

        imageBitmap?.let { canvas.drawBitmap(it, matrix, imagePaint) }

        gradientDrawable?.let { drawable ->
            val gradientHeight = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                107f,
                resources.displayMetrics
            ).toInt()

            val top = height - gradientHeight
            drawable.setBounds(0, top, width, height)

            drawable.draw(canvas)
        }
        canvas.drawPath(path, strokePaint)
    }
}
