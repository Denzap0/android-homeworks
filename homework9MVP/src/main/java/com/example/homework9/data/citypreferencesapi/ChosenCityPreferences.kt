package com.example.homework9.data.citypreferencesapi

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface ChosenCityPreferences {

    fun setCity(city : String)

    fun getCity() : String?
}