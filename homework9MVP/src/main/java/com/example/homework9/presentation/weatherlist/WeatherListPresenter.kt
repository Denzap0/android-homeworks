package com.example.homework9.presentation.weatherlist

import com.example.homework9.data.citiesbaseapi.CityBaseData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface WeatherListPresenter {
    fun fetchWeatherList()

    fun getCityCoordinates(city : String) : Single<Pair<Double,Double>>

    fun getWeatherList(coordinatesPair : Pair<Double,Double>)

    fun addCityIntoBase(cityBaseData: CityBaseData) : Completable

    fun getCitiesFromBase() : Single<List<CityBaseData>>

    fun close()
}