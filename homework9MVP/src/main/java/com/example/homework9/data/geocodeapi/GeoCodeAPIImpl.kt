package com.example.homework9.data.geocodeapi

import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient

class GeoCodeAPIImpl : GeoCodeAPI {
    private val requestFactory = RequestFactoryImpl()
    private val httpClient = OkHttpClient()
    override fun getTopHeadLines(city: String): Single<Pair<Double, Double>> {
        val request = requestFactory.getTopLinesRequest(city)
        return Single.create{
            emitter ->
        }
    }
}