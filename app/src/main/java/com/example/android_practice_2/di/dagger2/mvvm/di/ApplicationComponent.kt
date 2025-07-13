package com.example.android_practice_2.di.dagger2.mvvm.di

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.android_practice_2.di.dagger2.mvvm.activity.Daggar2Practice2Activity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class, DatabaseModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(daggar2Practice2Activity: Daggar2Practice2Activity)

    fun getMap() : Map<Class<*>, ViewModel>

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : ApplicationComponent
    }

}