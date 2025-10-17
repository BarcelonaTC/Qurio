package com.barcelona.qurio.presentation.animation

import android.animation.ValueAnimator
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd

fun animatePoints(
    startValue: Int = 0,
    endValue: Int,
    duration: Long = 1000,
    onUpdate: (Int) -> Unit,
    onEnd: () -> Unit = {}
) {
    ValueAnimator.ofInt(startValue, endValue).apply {
        this.duration = duration
        interpolator = DecelerateInterpolator()

        addUpdateListener { animator ->
            onUpdate(animator.animatedValue as Int)
        }

        doOnEnd {
            onEnd()
        }

        start()
    }
}