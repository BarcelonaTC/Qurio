package com.barcelona.qurio

import android.app.Application
import com.barcelona.qurio.di.AppModule

class TriviaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppModule.init(this)
    }
}