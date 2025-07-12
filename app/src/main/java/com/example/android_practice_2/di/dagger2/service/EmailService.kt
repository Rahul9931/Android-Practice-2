package com.example.android_practice_2.di.dagger2.service

import android.util.Log
import javax.inject.Inject

class EmailService @Inject constructor() {
    fun send(to:String, from:String, body:String){
        Log.d("check_di","EmailService: Email Send")
    }
}