package com.example.homework9.data.temperaturepreferencesapi

import com.example.homework9.data.TempUnitType

interface TemperaturePrefs {

    fun isCelsius() : Boolean

    fun setIsCelsius(isCelsius : Boolean)
}