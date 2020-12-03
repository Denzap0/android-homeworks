package com.example.homework6

import android.content.SharedPreferences

class StorageTypePreference(preferences: SharedPreferences) {

    private val sharedPreferences = preferences

    fun saveIsExternal(isExternal : Boolean){
        val editor = sharedPreferences.edit().apply{
            putBoolean("isExternal", isExternal)
            apply()
        }
    }

    fun getIsExternal() : Boolean = sharedPreferences.getBoolean("isExternal", false)
}