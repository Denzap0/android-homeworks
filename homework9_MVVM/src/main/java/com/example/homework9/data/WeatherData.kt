package com.example.homework9.data

import java.util.Date

data class WeatherData(
    val name : String,
    val date: Date,
    val temperatureKelvin: Double,
    val weather: String,
    val iconType : String
)