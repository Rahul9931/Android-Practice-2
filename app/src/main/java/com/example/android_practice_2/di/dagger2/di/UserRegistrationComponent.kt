package com.example.android_practice_2.di.dagger2.di

import com.example.android_practice_2.di.dagger2.service.EmailService
import com.example.android_practice_2.di.dagger2.service.UserRegistrationService
import dagger.Component

@Component
interface UserRegistrationComponent {
    fun getUserRegistrationService():UserRegistrationService

    fun getEmailService():EmailService
}