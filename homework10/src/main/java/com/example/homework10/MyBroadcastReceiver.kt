package com.example.homework10

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import java.io.File

class MyBroadcastReceiver : BroadcastReceiver() {

    private val serviceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d("LOGSERVICE", "Service connected")
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d("LOGSERVICE", "Service disconnected")
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val serviceIntent = Intent(context, LogService::class.java)
        serviceIntent.putExtra("LogText", intent?.action)
        context?.startService(serviceIntent)
        context?.bindService(serviceIntent,serviceConnection,0)
    }
}