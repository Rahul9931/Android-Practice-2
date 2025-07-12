package com.example.android_practice_2.di.dagger2.repository

import android.util.Log
import javax.inject.Inject

class UserRepository @Inject constructor() {
    fun sendUser(email:String, password:String){
        Log.d("check_di","userRepository: user save in db")
    }
}