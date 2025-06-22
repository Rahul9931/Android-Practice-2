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
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.android_practice_2.R
import com.example.android_practice_2.service_practice.activity.ServicePracticeActivity
import kotlin.concurrent.thread

class ShowOfferService: Service() {

    private val TAG = "SHOWOFFERSERVICE"
    private val requestCode = 101
    private val channelId = "channelId"
    private val channelName = "channelName"
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG," oncreate called")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG," onStartCommand called")
        thread(start = true) {
            while (true){
                Log.d(TAG,"lOGGING MESSAGE")
                Thread.sleep(1000)
            }
        }
        startForegroundService()
        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startForegroundService(){
        createNotificationChannel()
        val notification = sendNotification()
        startForeground(111,notification)
    }

    fun getPendingIntent():PendingIntent{
        val intent = Intent(this, ServicePracticeActivity::class.java)
        return PendingIntent.getActivity(this,requestCode,intent, PendingIntent.FLAG_IMMUTABLE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel():NotificationChannel{
        val channel = NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT)
        val notificationManager = ContextCompat.getSystemService(this,NotificationManager::class.java)
        notificationManager!!.createNotificationChannel(channel)
        return channel
    }

    fun sendNotification():Notification{
        val notification = NotificationCompat.Builder(this,channelId)
            .setContentText("ForegroundService running")
            .setContentTitle("Foreground")
            .setContentIntent(getPendingIntent())
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .build()

        return notification
    }
}