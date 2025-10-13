package com.barcelona.qurio.di


import com.barcelona.qurio.presentation.MainActivity
import com.barcelona.qurio.presentation.fragment.CharacterDetailFragment
import com.barcelona.qurio.presentation.fragment.CharacterSelectionFragment
import com.barcelona.qurio.presentation.fragment.HomeFragment
import com.barcelona.qurio.presentation.fragment.OnBoardingFragment
import com.barcelona.qurio.presentation.fragment.ResultPlayFragment
import com.barcelona.qurio.presentation.fragment.StartPlayFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class, AppModule::class])
interface AppComponent {
    fun inject(fragment: StartPlayFragment)
    fun inject(fragment: OnBoardingFragment)
    fun inject(fragment: ResultPlayFragment)
    fun inject(mainActivity: MainActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: CharacterSelectionFragment)
    fun inject(fragment: CharacterDetailFragment)
}