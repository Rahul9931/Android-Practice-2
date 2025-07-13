package com.example.android_practice_2.di.dagger2.activity

import android.app.Application
import com.example.android_practice_2.di.dagger2.mvvm.di.ApplicationComponent
import com.example.android_practice_2.di.dagger2.mvvm.di.DaggerApplicationComponent

class FakerApplication : Application() {
    lateinit var applicationComponent : ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder().build()
    }

}