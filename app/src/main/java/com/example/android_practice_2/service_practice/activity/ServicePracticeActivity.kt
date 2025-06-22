package com.example.android_practice_2.service_practice.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.android_practice_2.R
import com.example.android_practice_2.databinding.ActivityServicePracticeBinding
import com.example.android_practice_2.service_practice.service.SocketForegroundService

class ServicePracticeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServicePracticeBinding
    private val TAG = "SocketDemo"
    private val messageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                "SOCKET_MESSAGE_RECEIVED" -> {
                    val message = intent.getStringExtra("message")
                    Log.d(TAG, "Broadcast received: $message")
                    // Update your UI here if needed
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_service_practice)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        registerReceiver(messageReceiver, IntentFilter("SOCKET_MESSAGE_RECEIVED"))

        if (isNetworkAvailable()) {
            // Start service with dynamic URL
            val serviceIntent = Intent(this, SocketForegroundService::class.java).apply {
                putExtra("SERVER_URL", "http://192.168.1.43:3000") // Pass your dynamic URL here
            }
            startForegroundService(serviceIntent)

        } else {
            Log.e(TAG, "No network available")
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(messageReceiver)
    }
}