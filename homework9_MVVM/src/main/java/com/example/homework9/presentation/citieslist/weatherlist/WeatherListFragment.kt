package com.example.homework9.presentation.citieslist.weatherlist

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework9.R
import com.example.homework9.view.WeatherDataView
import com.example.homework9.view.WeatherListAdapter

class WeatherListFragment(private val application: Application): Fragment(),
    WeatherListView {

    private val viewModelFactory = ViewModelFactory(application)
    private lateinit var viewModel : WeatherListViewModelImpl
    private lateinit var recyclerView: RecyclerView
    private lateinit var showWeather: ShowWeather
    private val weatherListAdapter by lazy {
        WeatherListAdapter { data ->
            application.getSharedPreferences("chosenCity", Context.MODE_PRIVATE).getString("chosenCity", "Minsk")
                ?.let {
                    showWeather.showWeather(
                        data
                    )
                }
        }
    }

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
        initViewModel()
        viewModel.fetchWeatherList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.close()
    }

    override fun onStartLoading() {

    }

    override fun onStopLoading() {

    }

    override fun showWeatherList(weatherList: List<WeatherDataView>) {
        weatherListAdapter.updateItems(weatherList)
        showWeather.showWeather(weatherList[0])
    }

    override fun onError(message: String) {

    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this,viewModelFactory).get(WeatherListViewModelImpl::class.java)
        viewModel.weatherListLiveData.observe(viewLifecycleOwner, {data -> showWeatherList(data)})
    }
}