package com.barcelona.qurio.presentation

import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.barcelona.qurio.QurioApp
import com.barcelona.qurio.R
import com.barcelona.qurio.service.UserPreferences
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private var keepSplash = true

    @Inject
    lateinit var userPreferences: UserPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        (application as QurioApp).appComponent.inject(this)
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplash }
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        handleInitialNavigation()

        splashAnimation(splashScreen)
        lifecycleScope.launch {
            delay(2000)
            keepSplash = false
        }
    }

    private fun handleInitialNavigation() {
        lifecycleScope.launch {
            userPreferences.isFirstLaunch.collect {
                if (it) {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.onBoardingFragment)
                } else {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.startPlayFragment)
                }
            }
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