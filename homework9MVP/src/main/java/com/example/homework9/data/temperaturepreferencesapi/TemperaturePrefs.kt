package com.example.homework9.data.temperaturepreferencesapi

import com.example.homework9.data.TempUnitType

interface TemperaturePrefs {

    fun getTemperatureType() : TempUnitType

    fun setTemperatureType(tempUnitType: TempUnitType)
}