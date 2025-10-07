package com.barcelona.qurio.ui.custom_view.statstics

import com.barcelona.qurio.R
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.use
import androidx.core.graphics.PathParser
import androidx.core.graphics.toColorInt

class StatisticsCustomCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var fillColor = "#1A1D23".toColorInt()
    private var strokeColor = "#55FFFFFF".toColorInt()
    private var strokeWidthPx = 3f
    private var pathData: String? = null

    private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private var svgPath: Path? = null

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.StatisticsCustomCardView,
            0,
            0
        ).use { typedArray ->
            fillColor = typedArray.getColor(
                R.styleable.StatisticsCustomCardView_fillColor,
                fillColor
            )
            strokeColor = typedArray.getColor(
                R.styleable.StatisticsCustomCardView_strokeColor,
                strokeColor
            )
            strokeWidthPx = typedArray.getDimension(
                R.styleable.StatisticsCustomCardView_strokeWidth,
                strokeWidthPx
            )

            val pathResId = typedArray.getResourceId(
                R.styleable.StatisticsCustomCardView_pathData,
                0
            )

            pathData = if (pathResId != 0) context.getString(pathResId)
            else typedArray.getString(R.styleable.StatisticsCustomCardView_pathData)
        }

        fillPaint.color = fillColor
        strokePaint.color = strokeColor
        strokePaint.strokeWidth = strokeWidthPx

        if (!pathData.isNullOrEmpty()) {
            svgPath = PathParser.createPathFromPathData(pathData)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        svgPath?.let { path ->
            val pathBounds = RectF()
            path.computeBounds(pathBounds, true)

            val scaleX = width / pathBounds.width()
            val scaleY = height / pathBounds.height()

            val matrix = Matrix()
            matrix.setScale(scaleX, scaleY)
            matrix.postTranslate(
                -pathBounds.left * scaleX,
                -pathBounds.top * scaleY
            )

            val scaledPath = Path(path)
            scaledPath.transform(matrix)

            canvas.drawPath(scaledPath, fillPaint)
            canvas.drawPath(scaledPath, strokePaint)
        }
    }
}