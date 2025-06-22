package com.example.android_practice_2.deeplink_.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.android_practice_2.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Handler(Looper.getMainLooper()).postDelayed({
            handleDeepLink()
        }, 2000) // 2 seconds delay
    }

    private fun handleDeepLink() {
        val data = intent?.data

        when {
            data?.pathSegments?.contains("product") == true -> {
                val productId = data.lastPathSegment
                val intent = Intent(this, ProductActivity::class.java)
                intent.data = data
                startActivity(intent)
            }

            data?.pathSegments?.contains("category") == true -> {
                val categoryName = data.lastPathSegment
                val intent = Intent(this, CategoryActivity::class.java)
                intent.data = data
                startActivity(intent)
            }

            else -> {
                // No deep link, go to HomeActivity or MainActivity
                Log.d("check_deeplink","No screen")
            }
        }

        finish()
    }

}