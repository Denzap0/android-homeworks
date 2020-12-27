package com.example.homework9.presentation.citieslist.weatherlist

import com.example.homework9.data.citiesbaseapi.CityBaseData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface WeatherListPresenter {
    fun fetchWeatherList()

    fun showWeatherList(coordinatesPair : Pair<Double,Double>)

    fun close()
}