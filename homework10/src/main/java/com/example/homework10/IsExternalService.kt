package com.example.homework10

import android.content.SharedPreferences

class IsExternalService(private val sharedPreferences: SharedPreferences, private val storageChangedListener: StorageChangedListener) {
    fun saveIsExternal(isExternal : Boolean){
        sharedPreferences.edit().putBoolean("isExternal", isExternal).apply()
        storageChangedListener.storageChanged(isExternal)
    }

    fun getIsExternal() = sharedPreferences.getBoolean("isExternal", false)
}