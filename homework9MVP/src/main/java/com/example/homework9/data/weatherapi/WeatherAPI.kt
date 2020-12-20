package com.example.homework9.data.weatherapi

import com.example.homework9.presentation.weatherlist.WeatherDataPresenter
import io.reactivex.rxjava3.core.Single

interface WeatherAPI {

    fun getTopHeadLines(lat : Double, lon : Double) : Single<List<WeatherDataPresenter>>
}