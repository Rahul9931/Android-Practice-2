package com.example.android_practice_2.di.dagger2.mvvm.retrofit

import com.example.android_practice_2.di.dagger2.mvvm.model.ProductsItem
import retrofit2.Response
import retrofit2.http.GET

interface FakeAPI {
    @GET("products")
    suspend fun getProducts() : Response<List<ProductsItem>>
}