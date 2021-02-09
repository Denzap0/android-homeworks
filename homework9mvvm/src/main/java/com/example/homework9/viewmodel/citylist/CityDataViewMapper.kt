package com.example.homework9mvvm.viewmodel.citylist

import com.example.homework9mvvm.view.CityDataView

class CityDataViewMapper : (List<CityDataPresenter>) -> List<CityDataView> {
    override fun invoke(cityDataPresenterList: List<CityDataPresenter>): List<CityDataView> {
        val cityDataViewList = mutableListOf<CityDataView>()
        for(i in cityDataPresenterList.indices){
            cityDataViewList.add(CityDataView(cityDataPresenterList[i].name))
        }
        return cityDataViewList
    }
}