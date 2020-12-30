package com.example.homework9mvvm.viewmodel.weatherlist

import com.example.homework9mvvm.data.weatherapi.WeatherDataPresenter
import com.example.homework9mvvm.view.WeatherDataView

class WeatherDataViewListMapper : (List<WeatherDataPresenter>) -> List<WeatherDataView> {
    override fun invoke(listWeatherDataPresenter: List<WeatherDataPresenter>): List<WeatherDataView> {
        val listWeatherDataView = mutableListOf<WeatherDataView>()
        for (i in listWeatherDataPresenter.indices) {
            listWeatherDataView.add(
                WeatherDataView(
                    cityName = listWeatherDataPresenter[i].cityName,
                    date = listWeatherDataPresenter[i].date,
                    temperature = listWeatherDataPresenter[i].temperature,
                    weather = listWeatherDataPresenter[i].weather,
                    iconType = listWeatherDataPresenter[i].iconType
                )
            )
        }
        return listWeatherDataView
    }
}