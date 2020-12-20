package com.example.homework9.data.citiesbaseapi

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCity(cityBaseData: CityBaseData)

    @Query("SELECT * FROM cities_table ORDER BY id ASC")
    fun readAllCities() : LiveData<List<CityBaseData>>
}