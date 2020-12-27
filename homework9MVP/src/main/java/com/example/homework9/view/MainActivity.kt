package com.example.homework9.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.homework9.R
import com.example.homework9.databinding.ActivityMainBinding
import com.example.homework9.presentation.citieslist.weatherlist.ShowWeather
import com.example.homework9.presentation.citieslist.weatherlist.WeatherListFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

private const val ICONS_URL = "http://openweathermap.org/img/w/%s.png"

class MainActivity : AppCompatActivity(), ShowWeather {

    private lateinit var binding : ActivityMainBinding
    private lateinit var citiesActivityButton  : FloatingActionButton
    private lateinit var cityName : TextView
    private lateinit var cityTemp : TextView
    private lateinit var weatherDesc : TextView
    private lateinit var weatherImageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        citiesActivityButton = findViewById(R.id.citiesActivityButton)
        cityName = findViewById(R.id.cityName)
        cityTemp = findViewById(R.id.cityTemperature)
        weatherDesc = findViewById(R.id.weatherDescription)
        weatherImageView = findViewById(R.id.weatherImage)
        showWeatherListFragment()
        citiesActivityButton.setOnClickListener {
            val intent = Intent(this@MainActivity, CitiesActivity::class.java)
//            intent.putExtra("notify", notify)
            startActivityForResult(intent, 1000)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1000 && resultCode == 1){
            supportFragmentManager.beginTransaction().replace(R.id.weatherListFragment, WeatherListFragment(application))
                .commit()
        }
    }

    private fun showWeatherListFragment(){
        supportFragmentManager.beginTransaction()
            .add(R.id.weatherListFragment, WeatherListFragment(application))
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.temperature_type_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this@MainActivity, TemperatureTypeActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    override fun showWeather(weatherDataView: WeatherDataView, cityName : String) {
        this@MainActivity.cityName.text = cityName
        cityTemp.text = weatherDataView.temperature.toString()
        weatherDesc.text = weatherDataView.weather
        Glide.with(this.applicationContext).load(ICONS_URL.format(weatherDataView.iconType)).into(weatherImageView)
    }
}