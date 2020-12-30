package com.example.homework9mvvm.viewmodel.citylist

interface CitiesActivityPresenter {

    fun fetchCitiesList()

    fun addCity(cityName: String): Boolean

    fun getChosenCity() : String

    fun setChosenCity(chosenCityName: String)

    fun getCityCoordinates(cityName: String) : Pair<Double,Double>?
}