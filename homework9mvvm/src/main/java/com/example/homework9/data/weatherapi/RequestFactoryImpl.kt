package com.example.homework9mvvm.data.weatherapi

import okhttp3.Request

private const val API_KEY = "5677765bf7a096d11939d6eb31015b08"
private const val TOP_HEADLINES_URL = "https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&exclude=hour&appid=%s"

class RequestFactoryImpl : RequestFactory {
    override fun getTopHeadLinesRequest(lat : Double, lon : Double): Request =
        Request.Builder().url(TOP_HEADLINES_URL.format(lat, lon, API_KEY)).build()
}