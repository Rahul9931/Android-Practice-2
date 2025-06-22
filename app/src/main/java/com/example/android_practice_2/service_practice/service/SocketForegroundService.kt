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
import com.example.android_practice_2.service_practice.socket.SocketManager

class SocketForegroundService : Service() {
    private lateinit var socketManager: SocketManager
    private val TAG = "SocketService"
    private val CHANNEL_ID = "SocketServiceChannel"
    private val NOTIFICATION_ID = 101

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Service onCreate")
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification("Connecting..."))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val serverUrl = intent?.getStringExtra("SERVER_URL") ?: "http://192.168.1.43:3000"

        socketManager = SocketManager(this, serverUrl).apply {
            setListener(object : SocketManager.SocketListener {
                override fun onMessageReceived(message: String) {
                    updateNotification("New message received")
                }

                override fun onConnectionChanged(isConnected: Boolean) {
                    updateNotification(if (isConnected) "Connected" else "Disconnected")
                }

                override fun onError(error: String) {
                    Log.e(TAG, error)
                    updateNotification("Error: ${error.take(20)}...")
                }
            })
            connect()
        }

        return START_STICKY
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

    override fun onDestroy() {
        super.onDestroy()
        socketManager.disconnect()
        Log.d(TAG, "Service destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)

        stopSelf()
    }
}