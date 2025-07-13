package com.example.android_practice_2.di.dagger2.mvvm.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductsItem(
    val category: String,
    val description: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val image: String,
    val price: Double,
    val title: String
)