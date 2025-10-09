package com.barcelona.qurio.presentation.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.use
import androidx.core.graphics.PathParser
import com.barcelona.qurio.R

class StatisticsCustomCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var fillColor = context.getColor(R.color.surface_high)
    private var strokeColor = context.getColor(R.color.shade_quad)
    private var strokeWidthPx = 3f
    private var pathData: String? = null
    private var svgPath: Path? = null

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.FILL }
    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.STROKE }

    init {
        obtainAttributes(context, attrs)
        configurePaints()
        parsePath()
    }

    private fun obtainAttributes(context: Context, attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(attrs, R.styleable.StatisticsCustomCardView, 0, 0).use {
            fillColor = it.getColor(R.styleable.StatisticsCustomCardView_fillColor, fillColor)
            strokeColor = it.getColor(R.styleable.StatisticsCustomCardView_strokeColor, strokeColor)
            strokeWidthPx = it.getDimension(R.styleable.StatisticsCustomCardView_strokeWidth, strokeWidthPx)
            pathData = resolvePathData(context, it.getResourceId(R.styleable.StatisticsCustomCardView_pathData, 0), it)
        }
    }

    private fun resolvePathData(context: Context, resId: Int, typedArray: TypedArray) =
        if (resId != 0) context.getString(resId)
        else typedArray.getString(R.styleable.StatisticsCustomCardView_pathData)

    private fun configurePaints() {
        fillPaint.color = fillColor
        strokePaint.color = strokeColor
        strokePaint.strokeWidth = strokeWidthPx
    }

    private fun parsePath() {
        pathData?.takeIf { it.isNotEmpty() }?.let {
            svgPath = PathParser.createPathFromPathData(it)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        svgPath?.let { drawScaledPath(canvas, it) }
    }

    private fun drawScaledPath(canvas: Canvas, path: Path) {
        val scaledPath = scalePathToView(path)
        canvas.drawPath(scaledPath, fillPaint)
        canvas.drawPath(scaledPath, strokePaint)
    }

    private fun scalePathToView(path: Path): Path {
        val bounds = RectF()
        path.computeBounds(bounds, true)
        val matrix = Matrix().apply {
            setScale(width / bounds.width(), height / bounds.height())
            postTranslate(-bounds.left * width / bounds.width(), -bounds.top * height / bounds.height())
        }
        return Path(path).apply { transform(matrix) }
    }
}