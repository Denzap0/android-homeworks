package com.example.homework9.presentation.citieslist.TempViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefs
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefsAPIImpl

data class TempViewModelImpl(private val temperaturePrefs: TemperaturePrefsAPIImpl) : TempViewModel, ViewModel() {
    private val isCelsiusMutLiveData : MutableLiveData<Boolean> = MutableLiveData()
    val isCelsiusLiveData : LiveData<Boolean> = isCelsiusMutLiveData

    override fun getIsCelsius() {
        isCelsiusMutLiveData.value = temperaturePrefs.isCelsius()
    }


    override fun setIsCelsius(isCelsius : Boolean) {
        temperaturePrefs.setIsCelsius(isCelsius)
    }
}