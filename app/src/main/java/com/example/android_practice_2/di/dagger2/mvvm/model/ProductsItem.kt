package com.example.android_practice_2.di.dagger2.mvvm.model

data class ProductsItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val title: String
)