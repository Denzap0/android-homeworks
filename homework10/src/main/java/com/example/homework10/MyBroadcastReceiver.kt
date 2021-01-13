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

    private val serviceConnection = object: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Toast.makeText(activityContext, (service as LogService.LogServiceBinder).getServiceActions().getActionData().toString(), Toast.LENGTH_SHORT).show()
            activityContext?.stopService(serviceIntent)
        }

        override fun onServiceDisconnected(name: ComponentName?) {

        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        activityContext = context
        serviceIntent = Intent(context, LogService::class.java)
        serviceIntent.type = "remote"
        serviceIntent.putExtra("LogText", intent?.action)
        context?.startService(serviceIntent)
        context?.bindService(serviceIntent,serviceConnection,0)
    }

}