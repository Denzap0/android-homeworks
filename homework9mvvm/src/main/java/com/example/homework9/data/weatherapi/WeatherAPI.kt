package com.example.homework9mvvm.data.weatherapi

import com.example.homework9mvvm.data.TempUnitType
import io.reactivex.rxjava3.core.Single

interface WeatherAPI {

    fun getTopHeadLines(coordinatesPair : Pair<Double,Double>, tempUnitType: TempUnitType) : Single<List<WeatherDataPresenter>>
}