package com.example.android_practice_2.di.dagger2.mvvm.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android_practice_2.di.dagger2.mvvm.model.ProductsItem

@Dao
interface FakerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProducts(products : List<ProductsItem>)

    @Query("SELECT * FROM ProductsItem")
    suspend fun getProducts() : List<ProductsItem>
}