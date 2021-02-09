package com.example.homework9.presentation.citieslist.citylist

import com.example.homework9.view.CityDataView

interface CitiesListView {
    fun showCitiesList(citiesList : List<CityDataView>, chosenCityName : String)

    fun openDialog()

    fun showDialogError()

    fun closeDialog()
}