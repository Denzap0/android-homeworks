package com.example.homework9.presentation.weatherlist

import com.example.homework9.view.WeatherDataView

interface WeatherListView {

    fun onStartLoading()

    fun onStopLoading()

    fun showWeatherList(weatherList : List<WeatherDataView>)

    fun onError(message : String)
}