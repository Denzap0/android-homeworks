package com.example.homework9mvvm.viewmodel.citylist

import com.example.homework9mvvm.view.CityDataView

interface CitiesListView {
    fun onStartLoading()

    fun onStopLoading()

    fun onError(message : String)

    fun showCitiesList(citiesList : List<CityDataView>, chosenCityName : String)
}