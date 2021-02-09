package com.example.homework9.view

import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.homework9.R
import com.example.homework9.databinding.TemperatureTypeActivityBinding
import com.example.homework9.presentation.citieslist.TempPresent.TempPresent
import com.example.homework9.presentation.citieslist.TempPresent.TempPresentImpl

class TemperatureTypeActivity : AppCompatActivity() {

    private lateinit var binding : TemperatureTypeActivityBinding
    private lateinit var saveButton : Button
    private lateinit var tempPresenter : TempPresent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tempPresenter = TempPresentImpl(getSharedPreferences("isCelsius", Context.MODE_PRIVATE))
        binding = TemperatureTypeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.temperatureTypeSwitch.isChecked = tempPresenter.getIsCelsius()
        saveButton = findViewById(R.id.save_button)
        binding.temperatureTypeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            tempPresenter.setIsCelsius(isChecked)
        }
        saveButton.setOnClickListener{
            setResult(1)
            finish()
        }

    }
}