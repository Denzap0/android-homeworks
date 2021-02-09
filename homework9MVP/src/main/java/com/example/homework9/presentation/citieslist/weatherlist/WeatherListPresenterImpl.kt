package com.example.homework9.presentation.citieslist.weatherlist

import android.app.Application
import com.example.homework9.data.TempUnitType
import com.example.homework9.data.citiesbaseapi.CitiesBaseRepositoryImpl
import com.example.homework9.data.citiesbaseapi.CitiesRoomDataBase
import com.example.homework9.data.citypreferencesapi.ChosenCityPreferences
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefs
import com.example.homework9.data.weatherapi.WeatherAPI
import com.example.homework9.data.weatherapi.WeatherApiImpl
import com.example.homework9.data.weatherapi.WeatherDataPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

const val IMAGE_URL = "http://openweathermap.org/img/w/%s.png"
class WeatherListPresenterImpl(
    private var weatherListView: WeatherListView?,
    application: Application,
    private val chosenCityPreferencesImpl: ChosenCityPreferences,
    private val temperaturePrefs: TemperaturePrefs
) : WeatherListPresenter {

    private var repository: CitiesBaseRepositoryImpl
    private val weatherApi: WeatherAPI = WeatherApiImpl()
    private val weatherViewListMapper: (List<WeatherDataPresenter>) -> List<com.example.homework9.view.WeatherDataView> =
        WeatherDataViewListMapper()

    private lateinit var city : String

    init {
        val cityDao = CitiesRoomDataBase.getInstance(application).cityDao()
        repository = CitiesBaseRepositoryImpl(cityDao)
    }

    private var disposable: Disposable? = null

    override fun fetchWeatherList() {
        city = chosenCityPreferencesImpl.getCity()
        repository.getCity(city)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ cityData ->
                showWeatherList(Pair(cityData.lat, cityData.lon))
            }, {
                (Throwable("COORDINATES PAIR RETURN NULL"))
            })
    }

    override fun showWeatherList(coordinatesPair: Pair<Double, Double>) {

        disposable = weatherApi.getTopWeather(coordinatesPair, if(temperaturePrefs.isCelsius()) TempUnitType.CELSIUS else TempUnitType.FAHRENHEIT, city)
            .subscribeOn(Schedulers.computation())
            .map { data -> weatherViewListMapper(data) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { weatherList ->
                    chosenCityPreferencesImpl.getCity().let {
                        weatherListView?.showWeatherList(weatherList,
                            it
                        )
                    }
                },
                {
                }
            )
    }

    override fun close() {
        weatherListView = null
        disposable?.dispose()
    }


}