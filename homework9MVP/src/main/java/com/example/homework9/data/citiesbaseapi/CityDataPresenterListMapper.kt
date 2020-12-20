package com.example.homework9.data.citiesbaseapi

import com.example.homework9.presentation.citieslist.CityDataPresenter

class CityDataPresenterListMapper : (List<CityBaseData>) -> List<CityDataPresenter> {
    override fun invoke(cityBaseDataList: List<CityBaseData>): List<CityDataPresenter> {
        val cityDataPresenterList = mutableListOf<CityDataPresenter>()
        for (i in cityBaseDataList.indices) {
            cityDataPresenterList.add(
                CityDataPresenter(
                    name = cityBaseDataList[i].name,
                    lat = cityBaseDataList[i].lat,
                    lon = cityBaseDataList[i].lon
                )
            )
        }
        return cityDataPresenterList
    }
}