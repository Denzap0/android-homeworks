package com.example.homework9.data.citypreferencesapi

import android.content.SharedPreferences
import com.example.homework9.view.CityChangedNotify

class ChosenCityPreferencesImpl(private val sharedPreferences: SharedPreferences) :
    ChosenCityPreferences {
    override fun setCity(city: String){
        sharedPreferences.edit().putString("chosenCity", city).apply()
    }

    override fun getCity() : String? =
        sharedPreferences.getString("chosenCity", null)
}