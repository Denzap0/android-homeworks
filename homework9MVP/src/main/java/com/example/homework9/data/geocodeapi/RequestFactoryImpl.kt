package com.example.homework9.data.geocodeapi

import okhttp3.Request

private const val TOP_HEADLINES_URL = "https://geocode.xyz/%s?json=1"
class RequestFactoryImpl : RequestFactory{
    override fun getTopLinesRequest(city: String) =
        Request.Builder().url(TOP_HEADLINES_URL.format(city)).build()

}