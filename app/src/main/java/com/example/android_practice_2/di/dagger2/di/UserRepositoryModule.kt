package com.example.android_practice_2.di.dagger2.di

import com.example.android_practice_2.di.dagger2.repository.FirebaseRepository
import com.example.android_practice_2.di.dagger2.repository.SqlRepository
import com.example.android_practice_2.di.dagger2.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class UserRepositoryModule {

//    @Provides
//    fun getFirebaseRepository():UserRepository{
//        return FirebaseRepository()
//    }
    @Binds
    abstract fun getSqlRepository(sqlRepository: SqlRepository) : UserRepository
}