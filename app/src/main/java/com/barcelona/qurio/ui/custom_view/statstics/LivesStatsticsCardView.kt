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
//M4.78356 52.5038L1.43002 26.9808C0.751134 21.814 3.94342 16.9247 8.94687 15.4679L69.6266 0.71022C71.5438 0.243941 73.5464 0.25761 75.4571 0.750018L132.567 15.4679C139.072 16.8779 143.276 23.2075 142.053 29.751L137.646 53.32C136.79 57.8968 133.927 61.8484 129.844 64.0868C127.555 65.342 124.986 66 122.375 66H20.1691C17.6932 66 15.2533 65.4076 13.053 64.2722C8.5352 61.9408 5.44586 57.5443 4.78356 52.5038Z