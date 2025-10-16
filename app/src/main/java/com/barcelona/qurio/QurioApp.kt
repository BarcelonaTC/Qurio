package com.barcelona.qurio

import android.app.Application
import com.barcelona.qurio.di.AppComponent
import com.barcelona.qurio.di.AppModule
import com.barcelona.qurio.di.DaggerAppComponent
import com.barcelona.qurio.presentation.sounds.SoundPlayerManager

class QurioApp : Application() {
    lateinit var appComponent: AppComponent
    lateinit var soundPlayerManager: SoundPlayerManager
        private set
    override fun onCreate() {
        super.onCreate()
        AppModule.init(this)
        appComponent = DaggerAppComponent.create()
        soundPlayerManager = SoundPlayerManager(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        soundPlayerManager.release()
    }
}