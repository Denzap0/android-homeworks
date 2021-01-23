package com.example.homework10

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.SwitchCompat
import com.example.homework10.databinding.MainactivityBinding
import java.security.Permission
import java.security.Permissions
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private lateinit var switchStorage : SwitchCompat
    private lateinit var isExternalService: IsExternalService
    private lateinit var myBroadcastReceiver: MyBroadcastReceiver
    private lateinit var myIntentFilter : IntentFilter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)
        isExternalService = IsExternalService(getSharedPreferences("isExternal", MODE_PRIVATE), object : StorageChangedListener{
            override fun storageChanged(isExternal: Boolean) {

            }

        })
        switchStorage = findViewById(R.id.switchStorage)
        switchStorage.isChecked = isExternalService.getIsExternal()
        switchStorage.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked && this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                isExternalService.saveIsExternal(isChecked)
                switchStorage.isChecked = true
            }else if(!isChecked){
                switchStorage.isChecked = isChecked
                isExternalService.saveIsExternal(false)
            }
            else{
                this.requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1000)
                if(this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    switchStorage.isChecked = true
                    isExternalService.saveIsExternal(true)
                }else{
                    switchStorage.isChecked = false
                    isExternalService.saveIsExternal(true)
                }
            }

        }
        myIntentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        myIntentFilter.addAction(Intent.ACTION_LOCALE_CHANGED)
        myBroadcastReceiver = MyBroadcastReceiver()
        this.registerReceiver(myBroadcastReceiver,myIntentFilter)
    }

    override fun onPause() {
        super.onPause()

    }
}