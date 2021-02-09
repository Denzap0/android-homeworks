package com.example.homework9mvvm.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefsAPIImpl
import com.example.homework9.databinding.TemperatureTypeActivityBinding

class TemperatureTypeActivity : AppCompatActivity() {

    private lateinit var binding : TemperatureTypeActivityBinding
    private lateinit var temperaturePrefsAPI : TemperaturePrefsAPIImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TemperatureTypeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        temperaturePrefsAPI = TemperaturePrefsAPIImpl(getSharedPreferences("isCelsius", Context.MODE_PRIVATE))
        binding.temperatureTypeSwitch.isChecked = temperaturePrefsAPI.isCelsius()

        binding.temperatureTypeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            temperaturePrefsAPI.setIsCelsius(isChecked)
        }
    }
}