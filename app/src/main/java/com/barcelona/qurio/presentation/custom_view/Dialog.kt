package com.barcelona.qurio.presentation.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Shader
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.core.graphics.toColorInt
import com.barcelona.qurio.R
import androidx.core.content.withStyledAttributes

class Dialog @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val shapeView = DialogShapeView(context)
    private val titleCard = TitleCard(context)
    private val blueBox = View(context)
    private val closeIcon = ImageView(context)
    private val contentContainer = FrameLayout(context)
    private var onDismissListener: (() -> Unit)? = null

    init {
        context.withStyledAttributes(attrs, R.styleable.Dialog) {
            val titleText = getString(R.styleable.Dialog_dialogTitle)
            titleText?.let { titleCard.setTitle(it) }
        }

        val blueParams = LayoutParams(70.dp, 70.dp, Gravity.END or Gravity.TOP).apply {
            topMargin = 10.dp
        }
        blueBox.setBackgroundColor(context.getColor(R.color.primary))
        addView(blueBox, blueParams)

        val iconParams = LayoutParams(15.dp, 15.dp, Gravity.END or Gravity.TOP).apply {
            topMargin = 10.dp
        }
        closeIcon.apply {
            setImageResource(android.R.drawable.ic_menu_close_clear_cancel)
            setColorFilter(context.getColor(R.color.on_primary))
            setOnClickListener { dismiss() }
        }
        addView(closeIcon, iconParams)
        addView(
            shapeView, LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            ).apply {
                topMargin = 10.dp
            }
        )
        val titleParams = LayoutParams(
            145.dp,
            40.dp,
            Gravity.CENTER_HORIZONTAL or Gravity.TOP
        )
        addView(titleCard, titleParams)

        addView(
            contentContainer, LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT,
                Gravity.CENTER_HORIZONTAL
            ).apply {
                topMargin = 40.dp
            })
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child != null &&
            child != shapeView &&
            child != titleCard &&
            child != blueBox &&
            child != closeIcon &&
            child != contentContainer
        ) {
            contentContainer.addView(child, params)
        } else {
            super.addView(child, index, params)
        }
    }

    fun setOnDismissListener(listener: () -> Unit) {
        onDismissListener = listener
    }

    private fun dismiss() {
        onDismissListener?.invoke()
        animate()
            .alpha(0f)
            .setDuration(200)
            .withEndAction { (parent as? FrameLayout)?.removeView(this) }
            .start()
    }

    // 🟦 Custom background shape
    inner class DialogShapeView(context: Context) : View(context) {
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = "#0B0F14".toColorInt()
            style = Paint.Style.FILL
        }
        private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = context.getColor(R.color.shade_quad)
            style = Paint.Style.STROKE
            strokeWidth = 2f
            strokeJoin = Paint.Join.ROUND
        }

        private val cornerWidth = 25f
        private val cornerHeight = 25f
        private val baseWidth: Float
            get() {
                val displayMetrics = resources.displayMetrics
                val designReferenceWidth = 360f
                val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
                return 328f * (screenWidthDp / designReferenceWidth)
            }
        private val baseHeight = 260f

        private val gradientPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 2f
            strokeJoin = Paint.Join.ROUND
        }
        private var topEdge = Path()
        private var gradientShader: LinearGradient? = null
        override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
            super.onSizeChanged(w, h, oldw, oldh)
            val scaleX = w / baseWidth
            val cw = cornerWidth * scaleX

            val gradientWidth = (w - 2 * cw) * 0.90f
            val startX = (w - gradientWidth) / 2f
            val endX = startX + gradientWidth

            val gradientColors = intArrayOf(
                "#0A49BAF2".toColorInt(), // start transparent
                "#8049BAF2".toColorInt(), // fade in
                "#8049BAF2".toColorInt(), // under title
                "#8049BAF2".toColorInt(), // still under title
                "#0A49BAF2".toColorInt(), // fade out
            )
            val positions = floatArrayOf(
                0f,
                0.25f,
                0.5f,
                0.75f,
                1f
            )
            gradientShader = LinearGradient(
                startX, 1.5f,
                endX, 1.5f,
                gradientColors,
                positions,
                Shader.TileMode.CLAMP
            )
            gradientPaint.shader = gradientShader
            topEdge = Path().apply {
                moveTo(startX, 0.5f)
                lineTo(endX, 0.5f)
            }
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            val w = width.toFloat()
            val h = height.toFloat()

            val scaleX = w / baseWidth
            val scaleY = h / baseHeight

            val cw = cornerWidth * scaleX
            val ch = cornerHeight * scaleY

            val path = Path().apply {
                moveTo(cw, 0f)
                lineTo(w - cw, 0f)
                lineTo(w, ch)
                lineTo(w, h - ch)
                lineTo(w - cw, h)
                lineTo(cw, h)
                lineTo(0f, h - ch)
                lineTo(0f, ch)
                close()
            }

            canvas.drawPath(path, paint)
            canvas.drawPath(path, borderPaint)

            canvas.drawPath(topEdge, gradientPaint)
        }
    }

    private val Int.dp: Int
        get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            resources.displayMetrics
        ).toInt()
}
