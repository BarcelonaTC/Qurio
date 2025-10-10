package com.barcelona.qurio.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.barcelona.qurio.R
import com.barcelona.qurio.databinding.ResultItemBinding

class ResultCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding = ResultItemBinding.inflate(LayoutInflater.from(context), this)

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.ResultCardView, 0, 0).apply {
            try {
                val resultText = getString(R.styleable.ResultCardView_resultText)
                val blurVisible = getBoolean(R.styleable.ResultCardView_blurVisible, true)
                val starsVisible = getBoolean(R.styleable.ResultCardView_starsVisible, true)

                setResultText(resultText ?: "")
                showBlur(blurVisible)
                showStars(starsVisible)
            } finally {
                recycle()
            }
        }
    }

    fun setResultText(text: String) {
        binding.txtResult.text = text
    }

    fun showBlur(show: Boolean) {
        binding.blurBackground.isVisible = show
    }

    fun setBlurBackground(resId: Int) {
        binding.blurBackground.setImageResource(resId)
    }

    fun showStars(show: Boolean) {
        binding.starsContainer.isVisible = show
    }

    fun setReward(value: String) {
        binding.coinsReward.text = value
    }

    fun setCorrectCount(value: String) {
        binding.correctAnswersCount.text = value
    }

    fun setIncorrectCount(value: String) {
        binding.inCorrectAnswersCount.text = value
    }

    fun setSkippedCount(value: String) {
        binding.skippedAnswersCount.text = value
    }
}