package com.example.android_practice_2.di.dagger2.mvvm.di

import android.content.Context
import androidx.room.Room
import com.example.android_practice_2.di.dagger2.mvvm.db.FakerDB
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun getFakerDB(context:Context) : FakerDB{
        return Room.databaseBuilder(context, FakerDB::class.java, "fakerdb").build()
    }
}