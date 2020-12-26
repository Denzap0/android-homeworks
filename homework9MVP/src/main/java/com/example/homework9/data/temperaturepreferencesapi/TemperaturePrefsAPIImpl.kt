package com.example.homework9.data.temperaturepreferencesapi

import android.content.SharedPreferences
import com.example.homework9.data.TempUnitType

class TemperaturePrefsAPIImpl(private val sharedPreferences: SharedPreferences) : TemperaturePrefs {
    override fun isCelsius(): Boolean =
        sharedPreferences.getBoolean("isCelsius", true)

    override fun setIsCelsius(isCelsius : Boolean) {
        sharedPreferences.edit().putBoolean("isCelsius", isCelsius).apply()
    }
}