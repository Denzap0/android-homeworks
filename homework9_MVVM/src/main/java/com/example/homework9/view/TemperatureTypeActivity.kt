package com.example.homework9.view

import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.homework9.R
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefsAPIImpl
import com.example.homework9.databinding.TemperatureTypeActivityBinding

class TemperatureTypeActivity : AppCompatActivity() {

    private lateinit var binding : TemperatureTypeActivityBinding
    private lateinit var temperaturePrefsAPI : TemperaturePrefsAPIImpl
    private lateinit var saveButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TemperatureTypeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        temperaturePrefsAPI = TemperaturePrefsAPIImpl(getSharedPreferences("isCelsius", Context.MODE_PRIVATE))
        binding.temperatureTypeSwitch.isChecked = temperaturePrefsAPI.isCelsius()

        binding.temperatureTypeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            temperaturePrefsAPI.setIsCelsius(isChecked)
        }

        saveButton = findViewById(R.id.save_button)
        saveButton.setOnClickListener{
            setResult(1)
            finish()
        }
    }
}