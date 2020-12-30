package com.example.homework9.presentation.citieslist.citylist

import android.app.Application
import android.content.Context
import com.example.homework9.data.citiesbaseapi.CitiesBaseRepositoryImpl
import com.example.homework9.data.citiesbaseapi.CitiesRoomDataBase
import com.example.homework9.data.citiesbaseapi.CityBaseData
import com.example.homework9.data.citypreferencesapi.ChosenCityPreferences
import com.example.homework9.data.citypreferencesapi.ChosenCityPreferencesImpl
import com.example.homework9.data.geocodeapi.GeoCodeAPIImpl
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CitiesActivityViewModelmpl(
    private val cityListView: CitiesListView,
    private val application: Application

) : CitiesActivityViewModel {

    private val chosenCityPreferencesImpl: ChosenCityPreferences = ChosenCityPreferencesImpl(
        application.getSharedPreferences(
            "chosenCity",
            Context.MODE_PRIVATE
        )
    )
    private var citiesRepository: CitiesBaseRepositoryImpl
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

            })
        cityListView.onStopLoading()
    }

    override fun addCity(cityName: String): Boolean {
        cityListView.onStartLoading()
        var check = true
        geoCodeAPI.getTopHeadLines(cityName).subscribe(

            { pair ->
                citiesRepository.addCity(CityBaseData(null, cityName, pair.first, pair.second))
                    .subscribe(
                        {
                            citiesRepository.readAllCities()
                                .map { list -> cityDataViewMapper(list) }
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe { list ->
                                    setChosenCity(cityName)
                                    cityListView.showCitiesList(list, cityName)
                                }
                        }, {
                            check = false
                        })
            },
            {
                check = false
            }
        )
        cityListView.onStopLoading()
        return check
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