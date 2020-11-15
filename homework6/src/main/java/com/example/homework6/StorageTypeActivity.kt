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

        val sharedPrefs = getSharedPreferences("settingsFile", Context.MODE_PRIVATE)
        isExternal = sharedPrefs.getBoolean("isExternal", false)
        switchStorage.isChecked = sharedPrefs.getBoolean("isExternal", false)


        switchStorage.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                if (!isExternalStorageGranted()) {
                    switchStorage.isChecked = false
                    requestPermissions(
                        arrayOf(
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        ),
                        1000
                    )
                    if (isExternalStorageGranted()) {
                        isExternal = true
                        val editor = sharedPrefs.edit()
                        editor.putBoolean("isExternal", true)
                        editor.apply()
                    } else {
                        switchStorage.isChecked = false
                        val toast : Toast = Toast.makeText(this, "No permissions", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                } else {
                    isExternal = true
                    val editor = sharedPrefs.edit()
                    editor.putBoolean("isExternal", true)
                    editor.apply()
                }
            }
            if (!isChecked) {
                isExternal = false
                val editor = sharedPrefs.edit()
                editor.putBoolean("isExternal", false)
                editor.apply()
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
}