package com.example.android_practice_2.di.dagger2.mvvm.di

import com.example.android_practice_2.di.dagger2.mvvm.retrofit.FakeAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideFakerApi(retrofit: Retrofit) : FakeAPI{
        return retrofit.create(FakeAPI::class.java)
    }
}