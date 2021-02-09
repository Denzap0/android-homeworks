package com.example.homework9.view

import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.homework9.R
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefs
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefsAPIImpl
import com.example.homework9.databinding.TemperatureTypeActivityBinding
import com.example.homework9.presentation.citieslist.TempViewModel.TempViewModel
import com.example.homework9.presentation.citieslist.TempViewModel.TempViewModelFactory
import com.example.homework9.presentation.citieslist.TempViewModel.TempViewModelImpl

class TemperatureTypeActivity : AppCompatActivity() {

    private lateinit var binding : TemperatureTypeActivityBinding
    private lateinit var temperaturePrefsAPI : TemperaturePrefsAPIImpl
    private lateinit var saveButton : Button
    private lateinit var viewModel : TempViewModel
    private lateinit var tempViewModelFactory: TempViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TemperatureTypeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        temperaturePrefsAPI = TemperaturePrefsAPIImpl(getSharedPreferences("isCelsius", Context.MODE_PRIVATE))
        tempViewModelFactory = TempViewModelFactory(temperaturePrefsAPI)
        viewModel = ViewModelProvider(this, tempViewModelFactory).get(TempViewModelImpl::class.java)
        (viewModel as TempViewModelImpl).isCelsiusLiveData.observe(this, {isCelsius -> setSwitch(isCelsius)})
        viewModel.getIsCelsius()
        binding.temperatureTypeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setIsCelsius(isChecked)
        }

        saveButton = findViewById(R.id.save_button)
        saveButton.setOnClickListener{
            setResult(1)
            finish()
        }
    }

    private fun setSwitch(isChecked : Boolean){
        binding.temperatureTypeSwitch.isChecked = isChecked
    }
}