package com.example.homework6

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.storage_type_activity.*
import java.io.File
import java.util.jar.Manifest
import kotlin.properties.Delegates

class StorageTypeActivity : AppCompatActivity() {

    private var isExternal by Delegates.notNull<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.storage_type_activity)

        val storageTypePreference = StorageTypePreference(getSharedPreferences("settingsFile", Context.MODE_PRIVATE))
        isExternal = storageTypePreference.getIsExternal()
        switchStorage.isChecked = storageTypePreference.getIsExternal()


        switchStorage.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (!isExternalStorageGranted()) {
                    switchStorage.isChecked = false
                    Permissions.askPermission(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE), this)
                    if (isExternalStorageGranted()) {
                        isExternal = true
                        storageTypePreference.saveIsExternal(true)
                    } else {
                        switchStorage.isChecked = false
                        showNoPermissionsToast()
                    }
                } else {
                    isExternal = true
                    storageTypePreference.saveIsExternal(true)
                }
            }
            if (!isChecked) {
                isExternal = false
                storageTypePreference.saveIsExternal(false)
            }
        })

        backToMain.setOnClickListener(View.OnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        })
    }

    private fun isExternalStorageWritable(): Boolean =
        Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

    private fun isExternalStorageExists() =
        Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    private fun isExternalStorageGranted(): Boolean {
        return checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED
    }

    private fun showNoPermissionsToast(){
        Toast.makeText(this, "No permissions", Toast.LENGTH_SHORT).apply {
            show()
        }

    }
}