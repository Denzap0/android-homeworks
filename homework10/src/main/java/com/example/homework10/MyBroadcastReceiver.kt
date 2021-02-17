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

    private var activityContext: Context? = null
    private lateinit var serviceIntent: Intent

    override fun onReceive(context: Context?, intent: Intent?) {
        activityContext = context
        serviceIntent = Intent(context, LogService::class.java)
        serviceIntent.type = "remote"
        serviceIntent.putExtra("LogText", intent?.action)
        context?.startService(serviceIntent)

    }

}