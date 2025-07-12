package com.example.android_practice_2.di.dagger2.di

import com.example.android_practice_2.MainActivity
import com.example.android_practice_2.di.dagger2.activity.Dagger2PracticeActivity
import com.example.android_practice_2.di.dagger2.service.EmailService
import com.example.android_practice_2.di.dagger2.service.UserRegistrationService
import dagger.Component

@Component
interface UserRegistrationComponent {
    fun inject(dagger2PracticeActivity: Dagger2PracticeActivity)
}