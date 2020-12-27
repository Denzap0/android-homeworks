package com.example.homework9.presentation.citieslist.weatherlist

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.homework9.data.TempUnitType
import com.example.homework9.data.citiesbaseapi.CitiesBaseRepositoryImpl
import com.example.homework9.data.citiesbaseapi.CitiesRoomDataBase
import com.example.homework9.data.citiesbaseapi.CityBaseData
import com.example.homework9.data.citypreferencesapi.ChosenCityPreferences
import com.example.homework9.data.citypreferencesapi.ChosenCityPreferencesImpl
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefs
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefsAPIImpl
import com.example.homework9.data.weatherapi.WeatherAPI
import com.example.homework9.data.weatherapi.WeatherApiImpl
import com.example.homework9.data.weatherapi.WeatherDataPresenter
import com.example.homework9.view.CityChangedNotify
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherListPresenterImpl(
    private var weatherListView: WeatherListView?,
    application: Application,
) : WeatherListPresenter {

    private var repository: CitiesBaseRepositoryImpl
    private val chosenCityPreferencesImpl: ChosenCityPreferences = ChosenCityPreferencesImpl(
        application.getSharedPreferences(
            "chosenCity",
            Context.MODE_PRIVATE
        )
    )
    private val weatherApi: WeatherAPI = WeatherApiImpl()
    private val weatherViewListMapper: (List<WeatherDataPresenter>) -> List<com.example.homework9.view.WeatherDataView> =
        WeatherDataViewListMapper()
    private val temperaturePrefs: TemperaturePrefs = TemperaturePrefsAPIImpl(
        application.getSharedPreferences(
            "isCelsius",
            Context.MODE_PRIVATE
        )
    )

    init {
        val cityDao = CitiesRoomDataBase.getInstance(application).cityDao()
        repository = CitiesBaseRepositoryImpl(cityDao)
    }

    private var disposable: Disposable? = null

    override fun fetchWeatherList() {
        weatherListView?.onStartLoading()
        var city = chosenCityPreferencesImpl.getCity()
        if (city == null) {
            chosenCityPreferencesImpl.setCity("Minsk")
            repository.addCity(CityBaseData(null, "Minsk", 53.893009, 27.567444)).subscribe {
            }
        }else {
            Single.create<Pair<Double, Double>> { emitter ->

                repository.getCity(city).subscribe({ cityData ->
                    emitter.onSuccess(Pair(cityData.lat, cityData.lon))
                }, {
                    emitter.onError(Throwable("COORDINATES PAIR RETURN NULL"))
                })
            }.subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { coordinatesPair ->
                    showWeatherList(coordinatesPair)
                }
        }
    }

    override fun showWeatherList(coordinatesPair: Pair<Double, Double>) {

        weatherApi.getTopHeadLines(coordinatesPair, if(temperaturePrefs.isCelsius()) TempUnitType.CELSIUS else TempUnitType.FAHRENHEIT)
            .subscribeOn(Schedulers.computation())
            .map { data -> weatherViewListMapper(data) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { weatherList ->
                    weatherListView?.onStopLoading()
                    chosenCityPreferencesImpl.getCity()?.let {
                        weatherListView?.showWeatherList(weatherList,
                            it
                        )
                    }
                },
                {
                    weatherListView?.onError("ERROR")
                    weatherListView?.onStopLoading()
                }
            )
    }

    override fun close() {
        weatherListView = null
        disposable?.dispose()
    }


}