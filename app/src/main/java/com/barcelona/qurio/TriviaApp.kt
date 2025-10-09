package com.barcelona.qurio

import android.app.Application
import com.barcelona.qurio.di.AppComponent
import com.barcelona.qurio.di.AppModule
import com.barcelona.qurio.di.DaggerAppComponent

class TriviaApp : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        AppModule.init(this)
        appComponent = DaggerAppComponent.create()
    }
}