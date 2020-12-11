package com.example.homework9.WeatherAPI

import com.example.homework9.Model.WeatherData
import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody

interface WeatherAPI {

    fun getTopHeadLines(city : String) : Single<List<WeatherData>>
}