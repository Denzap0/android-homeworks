package com.example.homework9.data.weatherapi

import java.util.Date

 data class WeatherDataPresenter (
     val name : String,
    val date: Date,
    val temperature: Double,
    val weather: String,
    val iconType : String
)