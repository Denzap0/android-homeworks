package com.example.homework5_2.Settings

import android.content.SharedPreferences

class AsyncSettingsPreference(preferences: SharedPreferences) {

    private val sharedPrefs = preferences

    public fun saveAsyncType(asyncType: Int){
        val editor = sharedPrefs.edit().apply{
            putInt("asyncType", asyncType)
            apply()
        }
    }

    public fun loadAsyncType() : Int = sharedPrefs.getInt("asyncType", 0)

}