package com.example.homework9.data.citiesbaseapi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CityBaseData::class], version = 1)
abstract class CitiesRoomDataBase : RoomDatabase() {

    abstract fun cityDao(): CityDao

    companion object {
        @Volatile
        private var INSTANCE: CitiesRoomDataBase? = null

        fun getDataBase(context: Context): CitiesRoomDataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CitiesRoomDataBase::class.java,
                    "cities_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}