package com.example.homework9.presentation.citieslist.citylist

interface CitiesActivityPresenter {

    fun fetchCitiesList()

    fun addCity(cityName: String)

    fun getChosenCity() : String?

    fun setChosenCity(chosenCityName: String)
}