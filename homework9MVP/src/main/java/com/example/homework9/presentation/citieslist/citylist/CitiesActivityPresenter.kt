package com.example.homework9.presentation.citieslist.citylist

interface CitiesActivityPresenter {

    fun fetchCitiesList()

    fun addCity(cityName: String): Boolean

    fun getChosenCity() : String

    fun setChosenCity(chosenCityName: String)

    fun getCityCoordinates(cityName: String) : Pair<Double,Double>?
}