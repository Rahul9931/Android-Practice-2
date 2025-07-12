package com.example.android_practice_2.di.dagger2.di

import com.example.android_practice_2.di.dagger2.activity.Dagger2PracticeActivity
import dagger.Component

@Component(modules = [UserRepositoryModule::class])
interface UserRegistrationComponent {
    fun inject(dagger2PracticeActivity: Dagger2PracticeActivity)
}