package com.example.homework9.presentation.weatherlist

import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.disposables.Disposable

class WeatherListPresenterImpl : WeatherListPresenter{

    val liveData = MutableLiveData<List<WeatherDataPresenter>>()
    private val disposable : Disposable? = null

    override fun fetchWeatherList() {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}