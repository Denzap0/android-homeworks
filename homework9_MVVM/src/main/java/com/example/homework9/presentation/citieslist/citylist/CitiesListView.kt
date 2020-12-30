package com.example.homework9.presentation.citieslist.citylist

import com.example.homework9.view.CityDataView

interface CitiesListView {
    fun onStartLoading()

    fun onStopLoading()

    fun onError(message : String)

    fun showCitiesList(citiesList : List<CityDataView>, chosenCityName : String)
}