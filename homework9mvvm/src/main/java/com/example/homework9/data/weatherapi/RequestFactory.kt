package com.example.homework9mvvm.data.weatherapi

import okhttp3.Request

interface RequestFactory {

    fun getTopHeadLinesRequest(lat : Double, lon : Double) : Request
}