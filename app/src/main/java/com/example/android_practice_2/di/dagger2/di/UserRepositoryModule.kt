package com.example.android_practice_2.di.dagger2.di

import com.example.android_practice_2.di.dagger2.repository.FirebaseRepository
import com.example.android_practice_2.di.dagger2.repository.UserRepository
import dagger.Module
import dagger.Provides

@Module
class UserRepositoryModule {

    @Provides
    fun getFirebaseRepository():UserRepository{
        return FirebaseRepository()
    }
}