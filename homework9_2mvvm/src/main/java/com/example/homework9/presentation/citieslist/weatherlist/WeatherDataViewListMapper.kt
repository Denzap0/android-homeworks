package com.example.homework9.presentation.citieslist.weatherlist

import com.example.homework9.data.weatherapi.WeatherDataPresenter

class WeatherDataViewListMapper : (List<WeatherDataPresenter>) -> List<com.example.homework9.view.WeatherDataView> {
    override fun invoke(listWeatherDataPresenter: List<WeatherDataPresenter>): List<com.example.homework9.view.WeatherDataView> {
        val listWeatherDataView = mutableListOf<com.example.homework9.view.WeatherDataView>()
        for (i in listWeatherDataPresenter.indices) {
            listWeatherDataView.add(
                com.example.homework9.view.WeatherDataView(
                    name =  listWeatherDataPresenter[i].name,
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