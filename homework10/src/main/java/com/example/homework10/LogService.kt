package com.example.homework10

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.os.IBinder

class LogService : Service(), StorageChangedListener {

    private lateinit var myBroadcastReceiver : MyBroadcastReceiver
    private lateinit var myIntentFilter : IntentFilter
    private lateinit var isExternalService: IsExternalService
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        isExternalService = IsExternalService(getSharedPreferences("isExternal", MODE_PRIVATE),this)
        initBroadcastReceiver()
        myIntentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        myIntentFilter.addAction(Intent.ACTION_LOCALE_CHANGED)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        this.registerReceiver(myBroadcastReceiver,myIntentFilter)
        return START_REDELIVER_INTENT
    }

    override fun storageChanged(isExternal: Boolean) {
        initBroadcastReceiver()
    }
    private fun initBroadcastReceiver(){
        myBroadcastReceiver =
            MyBroadcastReceiver(if(isExternalService.getIsExternal())this.externalCacheDir!!.path else filesDir.path)
    }

}