package com.example.android_practice_2.di.dagger2.mvvm.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android_practice_2.di.dagger2.mvvm.model.ProductsItem

@Database(entities = [ProductsItem::class], version = 1)
abstract class FakerDB : RoomDatabase() {

    abstract fun getFakerDao() : FakerDao
}