package com.example.homework9mvvm.data.geocodeapi

import io.reactivex.rxjava3.core.Single

interface GeoCodeAPI {

    fun getTopHeadLines(city : String) : Single<Pair<Double, Double>>
}