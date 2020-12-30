package com.example.homework9mvvm.viewmodel.weatherlist

interface WeatherListViewModel {
    fun fetchWeatherList()

    fun showWeatherList(coordinatesPair : Pair<Double,Double>)

    fun close()
}