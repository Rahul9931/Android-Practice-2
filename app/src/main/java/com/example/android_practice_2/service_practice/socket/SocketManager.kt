package com.example.android_practice_2.service_practice.socket

import android.util.Log
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.net.URISyntaxException

class SocketManager private constructor() {
    companion object {
        private const val PING_INTERVAL = 15000L
        private var instance: SocketManager? = null

        fun getInstance(): SocketManager {
            return instance ?: synchronized(this) {
                instance ?: SocketManager().also { instance = it }
            }
        }
    }

    private val TAG = "SocketManager"
    private var mSocket: Socket? = null
    private var messageListener: ((String) -> Unit)? = null
    private var stateListener: ((Boolean) -> Unit)? = null
    private val handler = android.os.Handler()

    fun initialize(serverUrl: String) {
        try {
            val options = IO.Options().apply {
                reconnection = true
                reconnectionAttempts = Int.MAX_VALUE
                reconnectionDelay = 2000
                reconnectionDelayMax = 10000
                timeout = 20000
                transports = arrayOf("websocket")
                secure = false
                query = "platform=android&version=1.0"
            }

            mSocket = IO.socket(serverUrl, options).apply {
                on(Socket.EVENT_CONNECT, onConnect)
                on(Socket.EVENT_DISCONNECT, onDisconnect)
                on(Socket.EVENT_CONNECT_ERROR, onConnectError)
                //on(Socket.EVENT_CONNECT_TIMEOUT, onConnectTimeout)
                on("message", onMessageReceived)
            }

            startHeartbeat()
            connect()
        } catch (e: URISyntaxException) {
            Log.e(TAG, "Invalid server URL", e)
        }
    }

    fun connect() {
        mSocket?.connect()
    }

    fun disconnect() {
        handler.removeCallbacksAndMessages(null)
        mSocket?.disconnect()
        mSocket?.off()
        mSocket = null
    }

    fun sendMessage(message: String) {
        mSocket?.emit("client_message", message)
    }

    fun setMessageListener(listener: (String) -> Unit) {
        messageListener = listener
    }

    fun setConnectionStateListener(listener: (Boolean) -> Unit) {
        stateListener = listener
    }

    private fun startHeartbeat() {
        handler.postDelayed(heartbeatRunnable, PING_INTERVAL)
    }

    private val heartbeatRunnable = object : Runnable {
        override fun run() {
            if (mSocket?.connected() == true) {
                mSocket?.emit("ping", object : Ack {
                    override fun call(vararg args: Any?) {
                        Log.d(TAG, "Heartbeat acknowledged")
                    }
                })
            }
            handler.postDelayed(this, PING_INTERVAL)
        }
    }

    private val onConnect = Emitter.Listener {
        Log.d(TAG, "Socket connected")
        stateListener?.invoke(true)
    }

    private val onDisconnect = Emitter.Listener {
        Log.d(TAG, "Socket disconnected")
        stateListener?.invoke(false)
    }

    private val onConnectError = Emitter.Listener { args ->
        val error = args[0] as? Exception
        Log.e(TAG, "Connection error: ${error?.message}")
        stateListener?.invoke(false)
    }

    private val onConnectTimeout = Emitter.Listener {
        Log.e(TAG, "Connection timeout")
        stateListener?.invoke(false)
    }

    private val onMessageReceived = Emitter.Listener { args ->
        try {
            val data = args[0] as? JSONObject
            val message = data?.toString() ?: args[0].toString()
            Log.d(TAG, "Message received: $message")
            messageListener?.invoke(message)
        } catch (e: Exception) {
            Log.e(TAG, "Error processing message", e)
        }
    }
}