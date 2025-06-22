package com.example.android_practice_2.service_practice.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.android_practice_2.R
import com.example.android_practice_2.service_practice.activity.ServicePracticeActivity
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

class SocketForegroundService : Service() {
    private lateinit var socket: Socket
    private val TAG = "SocketService"
    private val CHANNEL_ID = "SocketServiceChannel"
    private val NOTIFICATION_ID = 101
    private val SERVER_URL = "http://192.168.1.43:3000"

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service onCreate")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification("Connecting..."))
        setupSocket()
    }

    private fun setupSocket() {
        try {
            val options = IO.Options().apply {
                reconnection = true
                reconnectionAttempts = Int.MAX_VALUE
                reconnectionDelay = 2000
                timeout = 20000
                transports = arrayOf("websocket")
                secure = false
                query = "platform=android"
            }

            socket = IO.socket(SERVER_URL, options).apply {
                on(Socket.EVENT_CONNECT, onConnect)
                on(Socket.EVENT_DISCONNECT, onDisconnect)
                on(Socket.EVENT_CONNECT_ERROR, onConnectError)
                on("server_message", onMessageReceived)
            }

            socket.connect()
        } catch (e: Exception) {
            Log.e(TAG, "Socket initialization error", e)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Socket Service Channel",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Keeps socket connection alive"
            }
            (getSystemService(NotificationManager::class.java)
                .createNotificationChannel(serviceChannel))
        }
    }

    private fun createNotification(text: String): Notification {
        val intent = Intent(this, ServicePracticeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Socket Service")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .setOnlyAlertOnce(true)
            .build()
    }

    private fun updateNotification(text: String) {
        val notification = createNotification(text)
        (getSystemService(NotificationManager::class.java)
            .notify(NOTIFICATION_ID, notification))
    }

    private val onConnect = Emitter.Listener {
        Log.d(TAG, "✅ Connected to server")
        updateNotification("Connected to server")
    }

    private val onDisconnect = Emitter.Listener {
        Log.d(TAG, "❌ Disconnected from server")
        updateNotification("Disconnected from server")
    }

    private val onConnectError = Emitter.Listener { args ->
        val error = args[0] as? Exception
        Log.e(TAG, "Connection error: ${error?.message}")
        updateNotification("Connection error")
    }

    private val onMessageReceived = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONObject
            val time = data.getString("time")
            val message = data.getString("message")
            Log.d(TAG, "📩 Received: $message at $time")

            // Broadcast message to activity
            Intent().apply {
                action = "SOCKET_MESSAGE_RECEIVED"
                putExtra("message", "$message at $time")
            }.also { intent ->
                sendBroadcast(intent)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing message", e)
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
        socket.off()
        Log.d(TAG, "Service destroyed")
    }
}