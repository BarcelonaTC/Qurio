package com.barcelona.qurio.di


import com.barcelona.qurio.presentation.MainActivity
import com.barcelona.qurio.presentation.fragment.OnBoardingFragment
import com.barcelona.qurio.presentation.fragment.StartPlayFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class])
interface AppComponent {
    fun inject(fragment: StartPlayFragment)
    fun inject(fragment: OnBoardingFragment)
    fun inject(mainActivity: MainActivity)
}