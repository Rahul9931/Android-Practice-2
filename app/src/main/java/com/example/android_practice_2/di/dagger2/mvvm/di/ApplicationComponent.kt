package com.example.android_practice_2.di.dagger2.mvvm.di

import com.example.android_practice_2.di.dagger2.mvvm.activity.Daggar2Practice2Activity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {

    fun inject(daggar2Practice2Activity: Daggar2Practice2Activity)

}