package com.example.homework9mvvm.data

import java.util.Date

data class WeatherData(
    val cityName : String,
    val date: Date,
    val temperatureKelvin: Double,
    val weather: String,
    val iconType : String
)