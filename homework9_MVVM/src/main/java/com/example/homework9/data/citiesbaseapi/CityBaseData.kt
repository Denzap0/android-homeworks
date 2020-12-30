package com.example.homework9.data.citiesbaseapi

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities_table")
data class CityBaseData(
    @PrimaryKey(autoGenerate = true) val id: Int?,
    val name: String,
    val lat: Double,
    val lon: Double
)