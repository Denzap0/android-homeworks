package com.example.homework9.presentation.weatherlist

import android.app.Application
import android.app.SharedElementCallback
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.example.homework9.data.citiesbaseapi.CitiesBaseRepository
import com.example.homework9.data.citiesbaseapi.CitiesRoomDataBase
import com.example.homework9.data.citiesbaseapi.CityBaseData
import com.example.homework9.data.citypreferencesapi.ChosenCityPreferences
import com.example.homework9.data.citypreferencesapi.ChosenCityPreferencesImpl
import com.example.homework9.data.geocodeapi.GeoCodeAPI
import com.example.homework9.data.geocodeapi.GeoCodeAPIImpl
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefs
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefsAPIImpl
import com.example.homework9.data.weatherapi.WeatherAPI
import com.example.homework9.data.weatherapi.WeatherApiImpl
import com.example.homework9.data.weatherapi.WeatherDataPresenter
import com.example.homework9.view.WeatherDataView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherListPresenterImpl(
    private var weatherListView: WeatherListView?,
    application: Application,
) : WeatherListPresenter {

    private lateinit var repository: CitiesBaseRepository
    private val chosenCityPreferencesImpl: ChosenCityPreferences = ChosenCityPreferencesImpl(application.getSharedPreferences(
        "isCelsius",
        Context.MODE_PRIVATE
    ))
    private val weatherApi: WeatherAPI = WeatherApiImpl()
    private val weatherViewListMapper: (List<WeatherDataPresenter>) -> List<WeatherDataView> = WeatherDataViewListMapper()
    private val geoCodeAPI: GeoCodeAPI = GeoCodeAPIImpl()
    private val temperaturePrefs: TemperaturePrefs = TemperaturePrefsAPIImpl(
        application.getSharedPreferences(
            "isCelsius",
            Context.MODE_PRIVATE
        )
    )

    init {
        val cityDao = CitiesRoomDataBase.getInstance(application.baseContext).cityDao()
        repository = CitiesBaseRepository(cityDao)
    }

    private var disposable: Disposable? = null

    override fun fetchWeatherList() {
        weatherListView?.onStartLoading()
        getCitiesFromBase().subscribe{data ->
            if(data.isEmpty()){
                addCityIntoBase(CityBaseData(1,"Minsk", 53.893009, 27.567444))
                getWeatherList(Pair(53.893009,27.567444))
            }else{
                getCityCoordinates(chosenCityPreferencesImpl.getCity()).subscribe { pair ->
                    getWeatherList(pair)
                }
            }
        }
    }


    override fun getCityCoordinates(city: String): Single<Pair<Double, Double>> =
        Single.create<Pair<Double, Double>> { emitter ->
            repository.getCity(city)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    override fun getWeatherList(coordinatesPair: Pair<Double, Double>) {
        disposable =
            weatherApi.getTopHeadLines(coordinatesPair, temperaturePrefs.getTemperatureType())
                .subscribeOn(Schedulers.computation())
                .map { data -> weatherViewListMapper(data) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { weatherList ->
                        weatherListView?.onStopLoading()
                        weatherListView?.showWeatherList(weatherList)
                    },
                    {
                        weatherListView?.onError("ERROR")
                        weatherListView?.onStopLoading()
                    }
                )
    }

    override fun addCityIntoBase(cityBaseData: CityBaseData): Completable
        = Completable.create{emitter ->
        repository.addCity(cityBaseData)
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    override fun getCitiesFromBase(): Single<List<CityBaseData>>
        = Single.create<List<CityBaseData>>{emitter ->
            repository.readAllCities
        }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())



    override fun close() {
        weatherListView = null
        disposable?.dispose()
    }


}