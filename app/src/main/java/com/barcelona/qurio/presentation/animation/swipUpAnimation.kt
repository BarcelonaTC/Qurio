package com.barcelona.qurio.presentation.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.view.animation.PathInterpolator
import android.widget.ImageView
import com.barcelona.qurio.R

fun swipeUpAnimation(view: View) {
    val arrow1 = view.findViewById<ImageView>(R.id.arrow1)
    val arrow2 = view.findViewById<ImageView>(R.id.arrow2)
    val arrow3 = view.findViewById<ImageView>(R.id.arrow3)

    val arrows = listOf(arrow1, arrow2, arrow3)
    val alphaValues = listOf(1f, 0.60f, 0.38f)

    arrows.forEachIndexed { index, arrow ->
        arrow.alpha = 0f
        arrow.scaleX = 0.7f
        arrow.scaleY = 0.7f
    }

    fun animateArrow(arrow: ImageView, index: Int, maxAlpha: Float) {
        val baseDelay = index * 120L
        val startY = 40f

        val animator = AnimatorSet().apply {
            playSequentially(
                AnimatorSet().apply {
                    playTogether(
                        ObjectAnimator.ofFloat(arrow, "alpha", 0f, maxAlpha).setDuration(500),
                        ObjectAnimator.ofFloat(arrow, "translationY", startY, startY - 15f)
                            .setDuration(500),
                        ObjectAnimator.ofFloat(arrow, "scaleX", 0.7f, 1f).setDuration(500),
                        ObjectAnimator.ofFloat(arrow, "scaleY", 0.7f, 1f).setDuration(500)
                    )
                },
                AnimatorSet().apply {
                    playTogether(
                        ObjectAnimator.ofFloat(arrow, "alpha", maxAlpha, 0f).setDuration(800),
                        ObjectAnimator.ofFloat(arrow, "translationY", startY - 15f, startY - 50f)
                            .setDuration(800),
                        ObjectAnimator.ofFloat(arrow, "scaleX", 1f, 0.8f).setDuration(800),
                        ObjectAnimator.ofFloat(arrow, "scaleY", 1f, 0.8f).setDuration(800)
                    )
                }
            )

            startDelay = baseDelay
            interpolator = PathInterpolator(0.33f, 0f, 0.2f, 1f)

            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    arrow.postDelayed({
                        arrow.translationY = 0f
                        arrow.alpha = 0f
                        arrow.scaleX = 0.7f
                        arrow.scaleY = 0.7f
                        start()
                    }, 300L - baseDelay)
                }
            })
        }

        animator.start()
    }

    arrows.forEachIndexed { index, arrow ->
        animateArrow(arrow, index, alphaValues[index])
    }
}

@SuppressLint("ClickableViewAccessibility")
fun setupSwipeUpGesture(view: View, onSwipeComplete: () -> Unit) {
    val swipeImage = view.findViewById<ImageView>(R.id.swipe_image)

    var initialY = 0f
    var dY = 0f
    val threshold = 40f

    swipeImage.setOnTouchListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                initialY = v.y
                dY = v.y - event.rawY
                true
            }
            MotionEvent.ACTION_MOVE -> {
                val newY = event.rawY + dY

                if (newY <= initialY && newY >= 0) {
                    v.y = newY
                }
                true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                val currentY = v.y
                val reachedTop = currentY <= threshold

                if (reachedTop) {
                    v.animate()
                        .y(0f)
                        .setDuration(150)
                        .setInterpolator(DecelerateInterpolator())
                        .withEndAction {
                            onSwipeComplete()
                            v.postDelayed({
                                v.animate()
                                    .y(initialY)
                                    .setDuration(300)
                                    .setInterpolator(OvershootInterpolator())
                                    .start()
                            }, 200)
                        }
                        .start()
                } else {
                    v.animate()
                        .y(initialY)
                        .setDuration(250)
                        .setInterpolator(OvershootInterpolator())
                        .start()
                }
                true
            }
            else -> false
        }
    }
}
