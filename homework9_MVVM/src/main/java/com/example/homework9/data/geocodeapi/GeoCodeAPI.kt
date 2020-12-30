package com.example.homework9.data.geocodeapi

import io.reactivex.rxjava3.core.Single

interface GeoCodeAPI {

    fun getTopHeadLines(city : String) : Single<Pair<Double, Double>>
}