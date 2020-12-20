package com.example.homework9.presentation.weatherlist

import java.util.Date

 data class WeatherDataPresenter (
    val date: Date,
    val temperature: Double,
    val weather: String,
    val iconType : String
)