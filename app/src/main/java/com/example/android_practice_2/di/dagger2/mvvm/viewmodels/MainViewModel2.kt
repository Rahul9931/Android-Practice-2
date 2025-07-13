package com.example.android_practice_2.di.dagger2.mvvm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_practice_2.di.dagger2.mvvm.repository.ProductRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel2 @Inject constructor(private val repository: ProductRepository) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.getProducts()
        }
    }

}