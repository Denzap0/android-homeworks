package com.example.homework9.presentation.citieslist.TempViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefsAPIImpl

class TempViewModelFactory(private val temperaturePrefs: TemperaturePrefsAPIImpl) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        modelClass.getConstructor(TemperaturePrefsAPIImpl::class.java).newInstance(temperaturePrefs)
}