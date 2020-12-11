package com.example.homework9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.homework9.Model.WeatherData
import com.example.homework9.WeatherAPI.WeatherApiImpl
import java.io.FileOutputStream
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weatherApiImpl = WeatherApiImpl()
        weatherApiImpl.getTopHeadLines("Yo").subscribe {
            weatherList -> Log.d("AAAA", weatherList.toString())
        }
    }
}