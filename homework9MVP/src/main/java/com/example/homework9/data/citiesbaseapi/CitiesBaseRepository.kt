package com.example.homework9.data.citiesbaseapi

class CitiesBaseRepository(private val cityDao : CityDao) {

    val readAllCities = cityDao.readAllCities()

    fun addCity(city : CityBaseData){
        cityDao.addCity(city)
    }
}