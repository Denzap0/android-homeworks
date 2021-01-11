package com.example.homework10

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.io.File

class MyBroadcastReceiver(private val fileDir : String) : BroadcastReceiver() {
    private lateinit var logFile : File
    override fun onReceive(context: Context?, intent: Intent?) {
        logFile = File(fileDir, "Log.txt")
        intent?.action?.let { logFile.appendText(it+ '\n')  }
    }
}