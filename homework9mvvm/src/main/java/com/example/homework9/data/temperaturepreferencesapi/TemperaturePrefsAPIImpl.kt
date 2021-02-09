package com.example.homework9mvvm.data.temperaturepreferencesapi

import android.content.SharedPreferences

class TemperaturePrefsAPIImpl(private val sharedPreferences: SharedPreferences) : TemperaturePrefs {
    override fun isCelsius(): Boolean =
        sharedPreferences.getBoolean("isCelsius", true)

    override fun setIsCelsius(isCelsius : Boolean) {
        sharedPreferences.edit().putBoolean("isCelsius", isCelsius).apply()
    }
}