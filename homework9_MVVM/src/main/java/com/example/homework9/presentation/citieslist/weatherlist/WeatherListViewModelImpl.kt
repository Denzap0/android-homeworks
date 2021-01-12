package com.example.homework9.presentation.citieslist.weatherlist

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homework9.data.TempUnitType
import com.example.homework9.data.citiesbaseapi.CitiesBaseRepositoryImpl
import com.example.homework9.data.citiesbaseapi.CitiesRoomDataBase
import com.example.homework9.data.citypreferencesapi.ChosenCityPreferences
import com.example.homework9.data.citypreferencesapi.ChosenCityPreferencesImpl
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefs
import com.example.homework9.data.temperaturepreferencesapi.TemperaturePrefsAPIImpl
import com.example.homework9.data.weatherapi.WeatherAPI
import com.example.homework9.data.weatherapi.WeatherApiImpl
import com.example.homework9.data.weatherapi.WeatherDataPresenter
import com.example.homework9.view.WeatherDataView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

const val ICONS_URL = "http://openweathermap.org/img/w/%s.png"
class WeatherListViewModelImpl(
    application: Application
) : WeatherListViewModel, ViewModel() {

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

    private val mutableWeatherListLiveData = MutableLiveData<List<WeatherDataView>>()
    val weatherListLiveData : LiveData<List<WeatherDataView>> = mutableWeatherListLiveData

    private var disposable: Disposable? = null

    override fun fetchWeatherList() {
        var city = chosenCityPreferencesImpl.getCity()
        Single.create<Pair<Double, Double>> { emitter ->
            repository.getCity(city).subscribe({ cityData ->
                emitter.onSuccess(Pair(cityData.lat, cityData.lon))
            }, {
                emitter.onError(Throwable("COORDINATES PAIR RETURN NULL"))
            })
        }.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { coordinatesPair ->
                showWeatherList(coordinatesPair, city)
            }
    }

    override fun showWeatherList(coordinatesPair: Pair<Double, Double>, cityName : String) {

        disposable = weatherApi.getTopWeather(coordinatesPair, if(temperaturePrefs.isCelsius()) TempUnitType.CELSIUS else TempUnitType.FAHRENHEIT, cityName)
            .subscribeOn(Schedulers.computation())
            .map { data -> weatherViewListMapper(data) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { weatherList ->
                    mutableWeatherListLiveData.value = weatherList
                },
                {
                    Throwable(it)
                }
            )
    }

    override fun close() {
        disposable?.dispose()
    }


}