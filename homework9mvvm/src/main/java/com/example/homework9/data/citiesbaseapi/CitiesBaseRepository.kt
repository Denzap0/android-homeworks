package com.example.homework9.data.citiesbaseapi

import com.example.homework9.presentation.citieslist.citylist.CityDataPresenter
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

interface CitiesBaseRepository {
    fun readAllCities() : Single<List<CityDataPresenter>>

    fun addCity(city : CityBaseData) : Completable

    fun getCity(nameOfCity : String) : Single<CityBaseData>


}