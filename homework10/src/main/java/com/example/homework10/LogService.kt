package com.example.homework10

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Binder
import android.os.IBinder
import java.io.File


class LogService : Service(), StorageChangedListener, ServiceActions {

    private lateinit var isExternalService: IsExternalService
    private lateinit var fileDir : String
    private var logText : String? = null

    override fun onBind(intent: Intent?): IBinder? = LogServiceBinder()

    override fun onCreate() {
        super.onCreate()
        isExternalService = IsExternalService(getSharedPreferences("isExternal", MODE_PRIVATE),this)
        setStorageDir(isExternalService.getIsExternal())

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logText = intent?.getStringExtra("LogText")
        if(logText != null) {
            File(fileDir, "Log.txt").appendText(logText + '\n')
        }
        return START_REDELIVER_INTENT
    }

    override fun storageChanged(isExternal: Boolean) {
        setStorageDir(isExternal)
    }

    private fun setStorageDir(isExternal: Boolean){
        if(isExternal){
            fileDir = filesDir.path
        }else if(this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            fileDir = this.externalCacheDir!!.path
        }
    }

    inner class LogServiceBinder : Binder(){
        fun getServiceActions() : ServiceActions =
            this@LogService
    }

    override fun getActionData(): String? = logText

}