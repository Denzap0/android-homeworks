package com.example.homework9.WeatherAPI

import okhttp3.Request

private const val API_KEY = "5677765bf7a096d11939d6eb31015b08"
private const val MINSK_COORDINATES_LAT = 53.893009
private const val MINSK_COORDINATES_LON = 27.567444
private const val TOP_HEADLINES_URL = "https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&exclude=hour&appid=%s"

class RequestFactoryImpl : RequestFactory {
    override fun getTopHeadLinesRequest(city: String): Request =
        Request.Builder().url(TOP_HEADLINES_URL.format(MINSK_COORDINATES_LAT, MINSK_COORDINATES_LON, API_KEY)).build()
}