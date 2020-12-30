package com.example.homework9mvvm.data.geocodeapi

import okhttp3.Request

interface RequestFactory {

    fun getTopLinesRequest(city : String) : Request
}