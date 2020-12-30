package com.example.homework9mvvm.data.citiesbaseapi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.homework9.data.citiesbaseapi.CityBaseData

@Dao
interface CityDao {

    @Insert
    fun addCity(cityBaseData: CityBaseData)

    @Query("SELECT * FROM cities_table ORDER BY id ASC")
    fun readAllCities() : List<CityBaseData>

    @Query("SELECT * FROM cities_table WHERE name = :city")
    fun getCityData(city : String) : CityBaseData?

}