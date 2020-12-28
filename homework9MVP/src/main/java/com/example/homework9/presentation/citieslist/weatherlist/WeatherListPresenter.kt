package com.example.homework9.presentation.citieslist.weatherlist

interface WeatherListPresenter {
    fun fetchWeatherList()

    fun showWeatherList(coordinatesPair : Pair<Double,Double>)

    fun close()
}