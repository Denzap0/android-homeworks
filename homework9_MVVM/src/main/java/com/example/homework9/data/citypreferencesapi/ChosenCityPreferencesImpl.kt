package com.example.homework9.data.citypreferencesapi

import android.content.SharedPreferences

class ChosenCityPreferencesImpl(private val sharedPreferences: SharedPreferences) :
    ChosenCityPreferences {
    override fun setCity(city: String){
        sharedPreferences.edit().putString("chosenCity", city).apply()
    }

    override fun getCity() : String? =
        sharedPreferences.getString("chosenCity", null)
}