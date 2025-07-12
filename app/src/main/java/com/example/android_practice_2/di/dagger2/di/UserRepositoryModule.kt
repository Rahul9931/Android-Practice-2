package com.example.android_practice_2.di.dagger2.di

import com.example.android_practice_2.ApplicationConstant
import com.example.android_practice_2.di.dagger2.di.qualifier.FirebaseQualifier
import com.example.android_practice_2.di.dagger2.repository.FirebaseRepository
import com.example.android_practice_2.di.dagger2.repository.SqlRepository
import com.example.android_practice_2.di.dagger2.repository.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class UserRepositoryModule {

    @Provides
    //@Named("firebase")
    //@Named(ApplicationConstant.firebase)
    @FirebaseQualifier
    fun getFirebaseRepository():UserRepository{
        return FirebaseRepository()
    }

    @Provides
    @Named("sql")
    fun getSqlRepository(sqlRepository: SqlRepository):UserRepository{
        return sqlRepository
    }
}