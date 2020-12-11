package com.example.homework9.WeatherAPI

import okhttp3.Request

interface RequestFactory {

    fun getTopHeadLinesRequest(city : String) : Request
}