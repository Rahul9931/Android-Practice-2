package com.example.android_practice_2.di.dagger2.mvvm.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android_practice_2.di.dagger2.mvvm.model.ProductsItem
import com.example.android_practice_2.di.dagger2.mvvm.retrofit.FakeAPI
import javax.inject.Inject

class ProductRepository @Inject constructor(private val fakeAPI: FakeAPI) {

    private val _product = MutableLiveData<List<ProductsItem>>()
    val product : LiveData<List<ProductsItem>>
        get() = _product

    suspend fun getProducts(){
        val result = fakeAPI.getProducts()
        if (result.isSuccessful && result.body() != null){
            _product.postValue(result.body())
        }
    }
}