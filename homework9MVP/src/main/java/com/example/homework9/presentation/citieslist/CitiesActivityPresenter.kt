package com.example.homework9.presentation.citieslist

import com.example.homework9.view.CityDataView

interface CitiesActivityPresenter {

    fun fetchCitiesList()

    fun addCity(cityName: String): Boolean

    fun getChosenCity() : String

    fun setChosenCity(chosenCityName: String)

    fun getCityCoordinates(cityName: String) : Pair<Double,Double>?
}