package com.example.android_practice_2.di.dagger2.repository

import android.util.Log
import javax.inject.Inject


interface UserRepository{
    fun sendUser(email:String, password:String)
}
class SqlRepository @Inject constructor() : UserRepository {
    override fun sendUser(email: String, password: String) {
        Log.d("check_di","userRepository: user save in sql repository")
    }

}

class FirebaseRepository : UserRepository {
    override fun sendUser(email: String, password: String) {
        Log.d("check_di","userRepository: user save in firebase repository")
    }

}