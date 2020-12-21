package com.example.homework9.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.homework9.R
import com.example.homework9.data.TempUnitType
import com.example.homework9.data.weatherapi.WeatherApiImpl
import com.example.homework9.databinding.ActivityMainBinding
import com.example.homework9.presentation.weatherlist.WeatherListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        showWeatherListFragment()
    }

    private fun showWeatherListFragment(){
        supportFragmentManager.beginTransaction()
            .add(R.id.weatherListFragment, WeatherListFragment(application))
            .commit()
    }
}