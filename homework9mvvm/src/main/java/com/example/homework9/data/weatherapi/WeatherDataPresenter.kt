package com.example.homework9mvvm.data.weatherapi

import java.util.Date

data class WeatherDataPresenter(
    val cityName: String,
    val date: Date,
    val temperature: Double,
    val weather: String,
    val iconType: String
)