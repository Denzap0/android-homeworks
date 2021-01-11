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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainactivity)
        isExternalService = IsExternalService(getSharedPreferences("isExternal", MODE_PRIVATE), object : StorageChangedListener{
            override fun storageChanged(isExternal: Boolean) {
                switchStorage.isChecked = isExternal
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
            }
            else{
                this.requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),1000)
                if(this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    isExternalService.saveIsExternal(isChecked)
                    switchStorage.isChecked = true
                }else{
                    switchStorage.isChecked = false
                }
            }

        }
        startService(Intent(this@MainActivity,LogService::class.java))
    }

    override fun onPause() {
        super.onPause()
        stopService(Intent(this@MainActivity,LogService::class.java))
    }
}