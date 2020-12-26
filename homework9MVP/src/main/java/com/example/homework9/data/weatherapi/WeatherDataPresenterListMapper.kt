package com.example.homework9.data.weatherapi

import com.example.homework9.data.TempUnitType
import com.example.homework9.data.WeatherData
import java.text.DecimalFormat

class WeatherDataPresenterListMapper(private val tempUnitType: TempUnitType) : (List<WeatherData>) -> List<WeatherDataPresenter> {
    override fun invoke(weatherDataList: List<WeatherData>): List<WeatherDataPresenter> {
        return when(tempUnitType){
            TempUnitType.CELSIUS -> mapWithCelsius(weatherDataList)
            TempUnitType.FAHRENHEIT -> mapWithFahrenheit(weatherDataList)
        }
    }

    private fun mapWithFahrenheit(weatherDataList: List<WeatherData>) : MutableList<WeatherDataPresenter>{
        var weatherDataPresentModelList = mutableListOf<WeatherDataPresenter>()
        for(i in weatherDataList.indices){
            weatherDataPresentModelList.add(
                WeatherDataPresenter(date = weatherDataList[i].date,
            iconType = weatherDataList[i].iconType,
            temperature = DecimalFormat("###.#").format(kelvinToFahrenheit(weatherDataList[i].temperatureKelvin)).toDouble(),
            weather = weatherDataList[i].weather)
            )
        }
        return weatherDataPresentModelList
    }

    private fun mapWithCelsius(weatherDataList: List<WeatherData>) : MutableList<WeatherDataPresenter>{
        var weatherDataPresentModelList = mutableListOf<WeatherDataPresenter>()
        for(i in weatherDataList.indices){
            weatherDataPresentModelList.add(
                WeatherDataPresenter(date = weatherDataList[i].date,
                iconType = weatherDataList[i].iconType,
                temperature = DecimalFormat("###.#").format(kelvinToCelsius(weatherDataList[i].temperatureKelvin)).toDouble(),
                weather = weatherDataList[i].weather)
            )
        }
        return weatherDataPresentModelList
    }

    private fun kelvinToCelsius(kelvin : Double ) = kelvin - 273.15

    private fun kelvinToFahrenheit(kelvin : Double ) = (kelvin - 273.15) * (9/5) + 32
}