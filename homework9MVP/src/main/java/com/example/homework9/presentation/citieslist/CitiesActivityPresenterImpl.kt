package com.example.homework9.presentation.citieslist

import android.app.Application
import android.content.Context
import android.util.Log
import com.example.homework9.data.citiesbaseapi.CitiesBaseRepositoryImpl
import com.example.homework9.data.citiesbaseapi.CitiesRoomDataBase
import com.example.homework9.data.citiesbaseapi.CityBaseData
import com.example.homework9.data.citypreferencesapi.ChosenCityPreferences
import com.example.homework9.data.citypreferencesapi.ChosenCityPreferencesImpl
import com.example.homework9.data.geocodeapi.GeoCodeAPIImpl
import com.example.homework9.view.CityDataView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CitiesActivityPresenterImpl(
    private val cityListView: CitiesListView,
    private val application: Application
) : CitiesActivityPresenter {

    private val chosenCityPreferencesImpl: ChosenCityPreferences = ChosenCityPreferencesImpl(
        application.getSharedPreferences(
            "chosenCity",
            Context.MODE_PRIVATE
        )
    )
    private lateinit var citiesRepository: CitiesBaseRepositoryImpl
    private val cityDataViewMapper = CityDataViewMapper()
    private val geoCodeAPI: GeoCodeAPIImpl = GeoCodeAPIImpl()

    init {
        val cityDao = CitiesRoomDataBase.getInstance(application.baseContext).cityDao()
        citiesRepository = CitiesBaseRepositoryImpl(cityDao)
    }

    override fun fetchCitiesList() {
        cityListView.onStartLoading()
        Single.create<List<CityDataPresenter>> { emitter ->
            citiesRepository.readAllCities().subscribe { list ->
                if (list != null) {
                    emitter.onSuccess(list)
                } else {
                    emitter.onError(Throwable("DB ERROR"))
                }

            }

        }.map { list -> cityDataViewMapper(list) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                cityListView.showCitiesList(
                    list,
                    getChosenCity()
                )
            }, {
                it
            })
        cityListView.onStopLoading()
    }

    override fun addCity(cityName: String): Boolean {
        cityListView.onStartLoading()
        var citiesList: List<CityDataView>? = null
        geoCodeAPI.getTopHeadLines(cityName).subscribe({ coordinatesPair ->
            citiesRepository.addCity(
                CityBaseData(
                    5,
                    cityName,
                    coordinatesPair.first,
                    coordinatesPair.second
                )
            ).subscribe({
                citiesRepository.readAllCities().map { list ->
                    cityDataViewMapper(list)
                }.subscribe { list ->
                    citiesList = list
                    chosenCityPreferencesImpl.setCity(cityName)
                    cityListView.showCitiesList(list, getChosenCity())
                }
            }, {
                "hi"
            })

        }, {

        })
        cityListView.onStopLoading()
        return if (citiesList != null) {
            cityListView.showCitiesList(citiesList!!, cityName)
            true
        } else {
            false
        }
    }

    override fun getChosenCity(): String {
        return if (chosenCityPreferencesImpl.getCity() != null) {
            chosenCityPreferencesImpl.getCity()!!
        } else {
            chosenCityPreferencesImpl.setCity("Minsk")
            chosenCityPreferencesImpl.getCity()!!
        }
    }


    override fun setChosenCity(chosenCityName: String) {
        chosenCityPreferencesImpl.setCity(chosenCityName)
    }

    override fun getCityCoordinates(cityName: String): Pair<Double, Double>? {
        var pair: Pair<Double, Double>? = null
        geoCodeAPI.getTopHeadLines(cityName).subscribe { coordinatesPair ->
            pair = coordinatesPair
        }
        return pair
    }


}