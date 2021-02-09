package com.example.homework9.presentation.citieslist.weatherlist

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework9.R
import com.example.homework9.data.citypreferencesapi.ChosenCityPreferences
import com.example.homework9.data.citypreferencesapi.ChosenCityPreferencesImpl
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefs
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefsAPIImpl
import com.example.homework9.view.WeatherDataView
import com.example.homework9.view.WeatherListAdapter

class WeatherListFragment(application: Application): Fragment(),
    WeatherListView {
    private val chosenCityPreferences : ChosenCityPreferences = ChosenCityPreferencesImpl(application.getSharedPreferences("chosenCity", Context.MODE_PRIVATE))
    private val temperaturePrefsAPI : TemperaturePrefs = TemperaturePrefsAPIImpl(application.getSharedPreferences("isCelsius", Context.MODE_PRIVATE))
    private val presenter = WeatherListPresenterImpl(this, application,chosenCityPreferences,temperaturePrefsAPI )
    private lateinit var recyclerView: RecyclerView
    private lateinit var showWeather: ShowWeather
    private val weatherListAdapter by lazy {
        WeatherListAdapter { data ->
            showWeather.showWeather(data)
        }
    }
    private lateinit var progressBar : ProgressBar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is ShowWeather){
            showWeather = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.weather_list_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.apply {
            adapter = weatherListAdapter
            layoutManager = LinearLayoutManager(this@WeatherListFragment.context,RecyclerView.VERTICAL,false)
        }
        presenter.fetchWeatherList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.close()
    }

    override fun showWeatherList(weatherList: List<WeatherDataView>, chosenCityName : String) {
        weatherListAdapter.updateItems(weatherList)
        showWeather.showWeather(weatherList[0])
    }

    override fun changeCityNameInAdapter() {

    }


}