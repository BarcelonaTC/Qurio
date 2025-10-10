package com.barcelona.qurio.presentation.animation

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import com.barcelona.qurio.R

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
                    animateToTop(
                        v,
                        initialY,
                        onSwipeComplete
                    )
                } else {
                    animateToStartPosition(
                        v,
                        initialY,
                    )
                }
                true
            }

            else -> false
        }
    }
}

private fun animateToTop(view: View, initialY: Float, onSwipeComplete: () -> Unit) {
    view.animate()
        .y(0f)
        .setDuration(150)
        .setInterpolator(DecelerateInterpolator())
        .withEndAction {
            onSwipeComplete()
            view.postDelayed({
                view.animate()
                    .y(initialY)
                    .setDuration(300)
                    .setInterpolator(OvershootInterpolator())
                    .start()
            }, 200)
        }
        .start()
}

private fun animateToStartPosition(
    view: View, initialY: Float,
) {
    view.animate()
        .y(initialY)
        .setDuration(250)
        .setInterpolator(OvershootInterpolator())
        .start()
}