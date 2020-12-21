package com.example.homework9.data.temperaturepreferencesapi

import android.content.SharedPreferences
import com.example.homework9.data.TempUnitType

class TemperaturePrefsAPIImpl(private val sharedPreferences: SharedPreferences) : TemperaturePrefs {
    override fun getTemperatureType(): TempUnitType =
        if (sharedPreferences.getBoolean("isCelsius", true))
            TempUnitType.CELSIUS
        else
            TempUnitType.FAHRENHEIT

    override fun setTemperatureType(tempUnitType: TempUnitType) {
        sharedPreferences.edit().putBoolean("isCelsius", tempUnitType == TempUnitType.CELSIUS).apply()
    }
}