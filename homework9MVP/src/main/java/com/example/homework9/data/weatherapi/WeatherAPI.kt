package com.example.homework9.data.weatherapi

import com.example.homework9.data.TempUnitType
import io.reactivex.rxjava3.core.Single

interface WeatherAPI {

    fun getTopHeadLines(coordinatesPair : Pair<Double,Double>, tempUnitType: TempUnitType) : Single<List<WeatherDataPresenter>>
}