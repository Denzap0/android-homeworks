package com.example.homework9.presentation.citieslist.weatherlist

import com.example.homework9.view.WeatherDataView

interface WeatherListView {

    fun showWeatherList(weatherList : List<WeatherDataView>, chosenCityName : String)

    fun changeCityNameInAdapter()
}