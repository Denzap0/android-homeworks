package com.example.homework9.data.citiesbaseapi

import android.util.Log

class CitiesBaseRepository(private val cityDao : CityDao) {

    val readAllCities = cityDao.readAllCities()

    fun addCity(city : CityBaseData){
        cityDao.addCity(city)
    }

    fun getCity(nameOfCity : String) : Pair<Double,Double>? {
        val city = cityDao.getCityData(nameOfCity)
        return Pair(city.lat,city.lon)
    }
}