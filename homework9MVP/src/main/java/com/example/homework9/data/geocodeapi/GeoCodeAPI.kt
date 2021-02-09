package com.example.homework9.data.geocodeapi

import io.reactivex.rxjava3.core.Single

interface GeoCodeAPI {

    fun getCityCode(city : String) : Single<Pair<Double, Double>>
}