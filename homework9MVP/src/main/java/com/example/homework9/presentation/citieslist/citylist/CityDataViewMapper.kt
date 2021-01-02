package com.example.homework9.presentation.citieslist.citylist

import com.example.homework9.view.CityDataView

class CityDataViewMapper : (List<CityDataPresenter>) -> List<CityDataView> {
    override fun invoke(cityDataPresenterList: List<CityDataPresenter>): List<CityDataView> =
        cityDataPresenterList.map { CityDataView(it.name) }
}