package com.example.homework9.presentation.weatherlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.homework9.view.WeatherDataView

class WeatherListFragment: Fragment(), WeatherListView {

    private val presenter = WeatherListPresenterImpl()



    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onStartLoading() {
        TODO("Not yet implemented")
    }

    override fun onStopLoading() {
        TODO("Not yet implemented")
    }

    override fun showWeatherList(weatherList: List<WeatherDataView>) {
        TODO("Not yet implemented")
    }

    override fun onError(message: String) {
        TODO("Not yet implemented")
    }


}