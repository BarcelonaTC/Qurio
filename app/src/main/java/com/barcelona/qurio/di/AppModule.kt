package com.barcelona.qurio.di

import android.content.Context
import dagger.Module

@Module
object AppModule {
    lateinit var appContext: Context
        private set

    fun init(context: Context) {
        appContext = context.applicationContext
    }

}
