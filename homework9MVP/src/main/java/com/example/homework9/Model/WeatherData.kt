package com.example.homework9.Model

import java.util.*
import java.util.concurrent.TimeUnit

data class WeatherData(
    val date: Date,
    val temperatureKelvin: Double,
    val weather: String,
    val iconType : String
)