package com.example.homework9.presentation.citieslist.weatherlist

import com.example.homework9.view.WeatherDataView

interface WeatherListView {

    fun onStartLoading()

    fun onStopLoading()

    fun showWeatherList(weatherList : List<WeatherDataView>, chosenCityName : String)

    fun onError(message : String)
}