package com.example.homework9.data.citiesbaseapi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CityBaseData::class], version = 1, exportSchema = false)
abstract class CitiesRoomDataBase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {
        private var citiesRoomDataBase: CitiesRoomDataBase? = null
        private val name = "cities_table"

        @Synchronized
        fun getInstance(context: Context): CitiesRoomDataBase {
            if (citiesRoomDataBase == null) {
                citiesRoomDataBase = Room.databaseBuilder(
                    context.applicationContext,
                    CitiesRoomDataBase::class.java,
                    name
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return citiesRoomDataBase as CitiesRoomDataBase
        }
    }
}