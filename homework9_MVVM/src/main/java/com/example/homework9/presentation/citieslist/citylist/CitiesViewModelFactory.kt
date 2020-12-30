package com.example.homework9.presentation.citieslist.citylist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.homework9.presentation.citieslist.weatherlist.ViewModelFactory

class CitiesViewModelFactory(private val application: Application) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.getConstructor(Application::class.java).newInstance(application)
}