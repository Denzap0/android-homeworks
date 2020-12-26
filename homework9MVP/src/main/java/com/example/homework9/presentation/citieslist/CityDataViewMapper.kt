package com.example.homework9.presentation.citieslist

import com.example.homework9.view.CityDataView

class CityDataViewMapper : (List<CityDataPresenter>) -> List<CityDataView> {
    override fun invoke(cityDataPresenterList: List<CityDataPresenter>): List<CityDataView> {
        val cityDataViewList = mutableListOf<CityDataView>()
        for(i in cityDataPresenterList.indices){
            cityDataViewList.add(CityDataView(cityDataPresenterList[i].name))
        }
        return cityDataViewList
    }
}