package com.barcelona.qurio

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.barcelona.qurio.presentation.animation.setupSwipeUpGesture
import com.barcelona.qurio.presentation.animation.swipeUpAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var keepSplash = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplash }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_view)
        swipeUpAnimation(findViewById(R.id.onboarding_view))
        setupSwipeUpGesture(findViewById(R.id.onboarding_view)){
            finish()
        }

        splashAnimation(splashScreen)
        lifecycleScope.launch {
            delay(2000)
            keepSplash = false
        }
    }

    private fun splashAnimation(splashScreen: SplashScreen) {
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            splashScreenView.view.animate()
                .setStartDelay(100L)
                .alpha(0f)
                .scaleX(1.1f)
                .scaleY(1.1f)
                .setDuration(600L)
                .setInterpolator(AccelerateDecelerateInterpolator())
                .withEndAction { splashScreenView.remove() }
                .start()
        }
    }
}