package com.example.homework9.presentation.citieslist.weatherlist

interface WeatherListViewModel {
    fun fetchWeatherList()

    fun showWeatherList(coordinatesPair : Pair<Double,Double>)

    fun close()
}