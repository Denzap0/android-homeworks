package com.example.homework9.data.citypreferencesapi

import android.content.SharedPreferences
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class ChosenCityPreferencesImpl(private val sharedPreferences: SharedPreferences) :
    ChosenCityPreferences {
    override fun setCity(city: String){
        sharedPreferences.edit().putString("chosenCity", city).apply()
    }

    override fun getCity() : String? =
        sharedPreferences.getString("chosenCity", null)
}