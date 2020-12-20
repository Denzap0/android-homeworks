package com.example.homework9.presentation.weatherlist

import com.example.homework9.view.WeatherDataView

class WeatherDataViewListMapper : (List<WeatherDataPresenter>) -> List<WeatherDataView> {
    override fun invoke(listWeatherDataPresenter: List<WeatherDataPresenter>): List<WeatherDataView> {
        val listWeatherDataView = mutableListOf<WeatherDataView>()
        for (i in listWeatherDataPresenter.indices) {
            listWeatherDataView.add(
                WeatherDataView(
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