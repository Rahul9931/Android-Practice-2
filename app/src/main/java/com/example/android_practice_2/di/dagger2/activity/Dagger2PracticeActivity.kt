package com.example.android_practice_2.di.dagger2.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android_practice_2.R
import com.example.android_practice_2.di.dagger2.di.DaggerUserRegistrationComponent
import com.example.android_practice_2.di.dagger2.di.UserRegistrationComponent
import dagger.internal.DaggerGenerated

class Dagger2PracticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dagger2_practice)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val component = DaggerUserRegistrationComponent.builder().build()
        val userRegistrationService = component.getUserRegistrationService()
        component.getEmailService()
        userRegistrationService.registerUser("example@gmail.com","12345")
    }
}