package com.example.android_practice_2.service_practice.socket

import android.content.Context
import android.content.Intent
import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

class SocketManager(private val context: Context, private val serverUrl: String) {
    private lateinit var socket: Socket
    private val TAG = "SocketManager"

    interface SocketListener {
        fun onMessageReceived(message: String)
        fun onConnectionChanged(isConnected: Boolean)
        fun onError(error: String)
    }

    private var listener: SocketListener? = null

    fun setListener(listener: SocketListener) {
        this.listener = listener
    }

    fun connect() {
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

            socket = IO.socket(serverUrl, options).apply {
                on(Socket.EVENT_CONNECT, onConnect)
                on(Socket.EVENT_DISCONNECT, onDisconnect)
                on(Socket.EVENT_CONNECT_ERROR, onConnectError)
                on("server_message", onMessageReceived)
            }

            socket.connect()
        } catch (e: Exception) {
            Log.e(TAG, "Socket initialization error", e)
            listener?.onError("Socket initialization error: ${e.message}")
        }
    }

    fun disconnect() {
        socket.disconnect()
        socket.off()
    }

    private val onConnect = Emitter.Listener {
        Log.d(TAG, "✅ Connected to server")
        listener?.onConnectionChanged(true)
    }

    private val onDisconnect = Emitter.Listener {
        Log.d(TAG, "❌ Disconnected from server")
        listener?.onConnectionChanged(false)
    }

    private val onConnectError = Emitter.Listener { args ->
        val error = args[0] as? Exception
        val errorMsg = "Connection error: ${error?.message}"
        Log.e(TAG, errorMsg)
        listener?.onError(errorMsg)
    }

    private val onMessageReceived = Emitter.Listener { args ->
        try {
            val data = args[0] as JSONObject
            val time = data.getString("time")
            val message = data.getString("message")
            val fullMessage = "$message at $time"
            Log.d(TAG, "📩 Received: $fullMessage")
            listener?.onMessageReceived(fullMessage)

            // Maintain backward compatibility with broadcast
            Intent().apply {
                action = "SOCKET_MESSAGE_RECEIVED"
                putExtra("message", fullMessage)
            }.also { intent ->
                context.sendBroadcast(intent)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing message", e)
            listener?.onError("Error parsing message: ${e.message}")
        }
    }
}