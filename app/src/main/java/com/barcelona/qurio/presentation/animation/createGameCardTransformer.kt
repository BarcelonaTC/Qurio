package com.barcelona.qurio.presentation.animation

import androidx.viewpager2.widget.ViewPager2

fun createGameCardTransformer(): ViewPager2.PageTransformer {
    return ViewPager2.PageTransformer { page, position ->
        val absPosition = kotlin.math.abs(position)


        page.scaleY = 1f
        page.scaleX = 1f


        page.rotationY = when {
            absPosition < 0.1f -> 0f
            else -> -position * 4f
        }

        page.rotationX = when {
            absPosition < 0.1f -> 0f
            else -> absPosition * 25f
        }

        page.translationX = -position * page.width * 0.05f
        page.translationY = absPosition * page.height * 0.05f

        page.alpha = when {
            absPosition >= 3f -> 0f
            else -> 1f
        }

        page.elevation = 0f

        page.translationZ = 0f

        page.cameraDistance = page.width * 15f
    }
}