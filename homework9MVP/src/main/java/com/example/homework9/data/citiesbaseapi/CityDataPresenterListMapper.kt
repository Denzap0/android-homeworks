package com.example.homework9.data.citiesbaseapi

import com.example.homework9.presentation.citieslist.citylist.CityDataPresenter

class CityDataPresenterListMapper : (List<CityBaseData>) -> List<CityDataPresenter> {
    override fun invoke(cityBaseDataList: List<CityBaseData>): List<CityDataPresenter> =
        cityBaseDataList.map { CityDataPresenter(it.name,it.lat,it.lon) }

}