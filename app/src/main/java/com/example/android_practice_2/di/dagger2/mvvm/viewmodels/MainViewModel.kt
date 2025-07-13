package com.example.android_practice_2.di.dagger2.mvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_practice_2.di.dagger2.mvvm.model.ProductsItem
import com.example.android_practice_2.di.dagger2.mvvm.repository.ProductRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProductRepository) : ViewModel() {


    val productsLiveData:LiveData<List<ProductsItem>>
        get() = repository.product
    init {
        viewModelScope.launch {
            repository.getProducts()
        }
    }
}