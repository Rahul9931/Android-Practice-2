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
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.android_practice_2.R
import com.example.android_practice_2.databinding.ActivityServicePracticeBinding
import com.example.android_practice_2.service_practice.service.ShowOfferService
import com.example.android_practice_2.service_practice.service.SocketForegroundService
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URISyntaxException

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this,R.layout.activity_service_practice)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Register broadcast receiver
        registerReceiver(messageReceiver, IntentFilter("SOCKET_MESSAGE_RECEIVED"))

        // Start the foreground service
        if (isNetworkAvailable()) {
            startService(Intent(this, SocketForegroundService::class.java))
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
        // Unregister receiver
        unregisterReceiver(messageReceiver)
        // Optionally stop service when activity is destroyed
        // stopService(Intent(this, SocketService::class.java))
    }

}