package com.barcelona.qurio.presentation.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.PathParser
import androidx.core.graphics.toColorInt
import com.barcelona.qurio.R
import androidx.core.graphics.withClip

class VolumeSlider @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val barBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.transparent)
        style = Paint.Style.FILL
    }

    private val barFillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val barBorderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.shade_quad)
        style = Paint.Style.STROKE
        strokeWidth = 2.dp
    }

    private val thumbPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.primary)
        setShadowLayer(20f, 0f, 0f, "#8049BAF2".toColorInt())
    }

    private val fillPath = PathParser.createPathFromPathData(
        "M0 2L0.560521 2.61934e-10L128.239 0L129 2L121.793 12H7.2067L0 2Z"
    )

    private val borderPath = PathParser.createPathFromPathData(
        "M168.697 0.5L169.471 2.98438L168.627 5.24316L158.257 17.5H11.745L1.12976 4.74805L0.518433 2.97168L1.08679 0.5H168.697Z"
    )

    private var progress = .9f
    private val barHeight = 16.dp
    private val thumbSize = 36.dp

    var onVolumeChange: ((Float) -> Unit)? = null

    private val plusIcon = AppCompatResources.getDrawable(context, R.drawable.ic_add)
    private val minusIcon = AppCompatResources.getDrawable(context, R.drawable.ic_remove)

    fun setVolumePercentage(percentage: Int) {
        val clamped = percentage.coerceIn(0, 100)
        progress = clamped / 100f
        onVolumeChange?.invoke(progress)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (layoutDirection == LAYOUT_DIRECTION_RTL) {
            canvas.save()
            canvas.translate(width.toFloat(), 0f)
            canvas.scale(-1f, 1f)
        }

        val cy = height / 2f
        val barRect = calculateBarRect(cy)
        val scaledPath = getScaledBarPath(barRect)

        drawBarBackground(canvas, scaledPath)
        drawProgressFill(canvas, barRect, scaledPath)
        drawBarBorder(canvas, barRect)
        drawThumb(canvas, barRect, cy)
        drawIcons(canvas, barRect, cy)

        if (layoutDirection == LAYOUT_DIRECTION_RTL) {
            canvas.restore()
        }
    }


    private fun calculateBarRect(cy: Float): RectF {
        val barLeft = paddingLeft + thumbSize / 2f
        val barRight = width - paddingRight - thumbSize / 2f
        val barTop = cy - barHeight / 2f
        val barBottom = cy + barHeight / 2f
        return RectF(barLeft, barTop, barRight, barBottom)
    }

    private fun getScaledBarPath(barRect: RectF): Path {
        val scaledPath = Path(fillPath)
        val scaleMatrix = Matrix().apply {
            val scaleX = barRect.width() / 129f
            val scaleY = barRect.height() / 12f
            setScale(scaleX, scaleY)
            postTranslate(barRect.left, barRect.top)
        }
        scaledPath.transform(scaleMatrix)
        return scaledPath
    }

    private fun drawBarBackground(canvas: Canvas, path: Path) {
        canvas.drawPath(path, barBackgroundPaint)
    }

    private fun drawProgressFill(canvas: Canvas, barRect: RectF, path: Path) {
        val fillWidth = barRect.left + barRect.width() * progress

        val clipRect = RectF(barRect.left, barRect.top, fillWidth, barRect.bottom)
        barFillPaint.shader = LinearGradient(
            barRect.left, 0f, fillWidth, 0f,
            context.getColor(R.color.primary_variant),
            context.getColor(R.color.primary_variant),
            Shader.TileMode.CLAMP
        )

        canvas.withClip(clipRect) {
            drawPath(path, barFillPaint)
        }
    }

    private fun drawBarBorder(canvas: Canvas, barRect: RectF) {
        val borderOffset = 4.dp

        val borderPath = Path(borderPath)

        val borderRect = RectF(
            barRect.left - borderOffset - 4.dp,
            barRect.top - borderOffset,
            barRect.right + borderOffset + 4.dp,
            barRect.bottom + borderOffset + 3.dp
        )

        val scaleMatrix = Matrix().apply {
            val scaleX = borderRect.width() / 170f
            val scaleY = borderRect.height() / 20f
            setScale(scaleX, scaleY)
            postTranslate(borderRect.left, borderRect.top)
        }

        borderPath.transform(scaleMatrix)

        barBorderPaint.strokeJoin = Paint.Join.BEVEL
        barBorderPaint.strokeCap = Paint.Cap.SQUARE
        canvas.drawPath(borderPath, barBorderPaint)
    }

    private fun drawThumb(canvas: Canvas, barRect: RectF, cy: Float) {
        val fillEndX = barRect.left + barRect.width() * progress

        val thumbX = fillEndX.coerceIn(
            barRect.left + thumbSize / 2f,
            barRect.right - thumbSize / 2f
        )

        val hexPath = createHexagon(thumbX, cy, thumbSize / 2f)
        canvas.drawPath(hexPath, thumbPaint)
    }

    private fun drawIcons(canvas: Canvas, barRect: RectF, cy: Float) {
        val iconSize = barHeight * 0.8f
        val iconY = cy - iconSize / 2
        val minusOffsetX = 16.dp
        val plusOffsetX = 16.dp

        minusIcon?.let {
            val left = barRect.left + minusOffsetX
            it.setBounds(
                left.toInt(),
                iconY.toInt(),
                (left + iconSize).toInt(),
                (iconY + iconSize).toInt()
            )
            it.draw(canvas)
        }

        plusIcon?.let {
            val right = barRect.right - plusOffsetX - iconSize
            it.setBounds(
                right.toInt(),
                iconY.toInt(),
                (right + iconSize).toInt(),
                (iconY + iconSize).toInt()
            )
            it.draw(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val barLeft = paddingLeft + thumbSize / 2f
                val barRight = width - paddingRight - thumbSize / 2f
                val rawProgress = ((event.x - barLeft) / (barRight - barLeft)).coerceIn(0f, 1f)

                val mappedProgress = ((rawProgress - 0.05f) / 0.9f).coerceIn(0f, 1f)

                progress = mappedProgress
                onVolumeChange?.invoke(progress)
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun createHexagon(cx: Float, cy: Float, radius: Float): Path {
        val basePath = PathParser.createPathFromPathData(
            "M14.9999 5.1547C16.2375 4.44017 17.7623 4.44017 18.9999 5.1547L27.1242 9.8453C28.3618 10.5598 29.1242 11.8803 29.1242 13.3094V22.6906C29.1242 24.1197 28.3618 25.4402 27.1242 26.1547L18.9999 30.8453C17.7623 31.5598 16.2375 31.5598 14.9999 30.8453L6.87552 26.1547C5.63792 25.4402 4.87552 24.1197 4.87552 22.6906V13.3094C4.87552 11.8803 5.63792 10.5598 6.87552 9.8453L14.9999 5.1547Z"
        )

        val bounds = RectF()
        basePath.computeBounds(bounds, true)

        val path = Path(basePath)

        // Compute scale factor based on desired radius
        val scale = (radius * 2f) / bounds.width()

        val matrix = Matrix().apply {
            setScale(scale, scale, bounds.centerX(), bounds.centerY())
            postTranslate(cx, cy - bounds.centerY())
        }

        path.transform(matrix)
        return path
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val measuredWidth = when (widthMode) {
            MeasureSpec.EXACTLY -> width
            MeasureSpec.AT_MOST -> width
            MeasureSpec.UNSPECIFIED -> 200.dp.toInt()
            else -> width
        }

        val measuredHeight = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize
            MeasureSpec.AT_MOST -> minOf(56.dp.toInt(), heightSize)
            MeasureSpec.UNSPECIFIED -> 56.dp.toInt()
            else -> heightSize
        }

        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    private val Int.dp: Float
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics
        )
}
