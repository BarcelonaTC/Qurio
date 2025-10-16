package com.barcelona.qurio.presentation.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.PathParser
import com.barcelona.qurio.R

class TitleCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    private val pathData =
        "M11.5055 33.2028L8.47336 21.6255C7.52986 18.0231 8.05377 14.1935 9.93015 10.9769C12.4503 6.65659 17.0755 4 22.0772 4H136.263C141.097 4 145.61 6.41571 148.292 10.4375C150.644 13.9663 151.329 18.3502 150.163 22.4281L146.673 34.6457C146.234 36.1825 145.36 37.5599 144.157 38.6123C142.76 39.8353 140.992 40.5543 139.138 40.6541L77 44L20.5894 40.6536C18.8912 40.5529 17.2468 40.0207 15.8116 39.1074C13.6829 37.7527 12.1448 35.6437 11.5055 33.2028Z"
    private val basePath = PathParser.createPathFromPathData(pathData)
    private val highlightPath = Path()
    private val mainPath = Path()
    private val bounds = RectF()

    private val highlightPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.primary_highlight)
        style = Paint.Style.FILL
    }

    private val mainPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.getColor(R.color.primary) // main blue
        style = Paint.Style.FILL
        setShadowLayer(12f, 0f, 6f, Color.argb(30, 73, 186, 242)) // drop shadow
    }

    private var titleTextView: TextView

    init {
        orientation = VERTICAL
        setWillNotDraw(false)
        setLayerType(LAYER_TYPE_SOFTWARE, null)

        titleTextView = TextView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            ).apply {
                setMargins(0, 8, 0, 8)
            }
            gravity = Gravity.CENTER
            setTextColor(context.getColor(R.color.on_primary))
            setTextAppearance(R.style.Title_Small) // same as XML style
        }

        addView(titleTextView)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleCard)
        val title = typedArray.getString(R.styleable.TitleCard_cardTitle)
        typedArray.recycle()

        title?.let { titleTextView.text = it }

        basePath.computeBounds(bounds, true)
    }

    fun setTitle(title: String) {
        titleTextView.text = title
    }

    var cardTitle: String?
        get() = titleTextView.text.toString()
        set(value) {
            titleTextView.text = value
        }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()

        val scaleMatrix = Matrix()
        val scaleX = w / bounds.width()
        val scaleY = h / (bounds.height() + 10)
        scaleMatrix.setScale(scaleX, scaleY)
        scaleMatrix.postTranslate(-bounds.left * scaleX, -bounds.top * scaleY + 15)

        mainPath.set(basePath)
        mainPath.transform(scaleMatrix)

        highlightPath.set(basePath)
        val highlightMatrix = Matrix(scaleMatrix)
        highlightMatrix.postScale(1.005f, 1f, w / 2f, 0f)
        highlightMatrix.postTranslate(0f, -10f)
        highlightPath.transform(highlightMatrix)

        canvas.drawPath(highlightPath, highlightPaint)
        canvas.drawPath(mainPath, mainPaint)
    }
}
