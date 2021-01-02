package com.example.homework9.presentation.citieslist.TempPresent

import android.content.SharedPreferences
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefs
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefsAPIImpl

class TempPresentImpl(private val sharedPreferences: SharedPreferences) : TempPresent {

    private val temperaturePrefs : TemperaturePrefs = TemperaturePrefsAPIImpl(sharedPreferences)
    override fun setIsCelsius(isCelsius : Boolean) {
        temperaturePrefs.setIsCelsius(isCelsius)
    }

    override fun getIsCelsius(): Boolean =
        temperaturePrefs.isCelsius()

}