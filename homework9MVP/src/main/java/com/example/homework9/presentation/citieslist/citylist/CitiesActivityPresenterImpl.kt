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
import io.reactivex.rxjava3.core.Completable
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
    private var citiesRepository: CitiesBaseRepositoryImpl
    private val cityDataViewMapper = CityDataViewMapper()
    private val geoCodeAPI: GeoCodeAPIImpl = GeoCodeAPIImpl()

    init {
        val cityDao = CitiesRoomDataBase.getInstance(application.baseContext).cityDao()
        citiesRepository = CitiesBaseRepositoryImpl(cityDao)
    }

    override fun fetchCitiesList() {
        citiesRepository.readAllCities().map {list -> cityDataViewMapper(list)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                getChosenCity().let {
                    cityListView.showCitiesList(
                        list,
                        it
                    )
                }
            }, {

            })
    }

    override fun addCity(cityName: String) {
        geoCodeAPI.getCityCode(cityName)
            .flatMap {
                return@flatMap Single.create<Completable> { emitter -> emitter.onSuccess(citiesRepository.addCity(CityBaseData(null, cityName, it.first, it.second))) }
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it.observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            citiesRepository.readAllCities()
                                .map { list -> cityDataViewMapper(list) }
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe { list ->
                                    setChosenCity(cityName)
                                    cityListView.showCitiesList(list, cityName)
                                }
                            cityListView.closeDialog()
                        }, {
                            cityListView.showDialogError()
                        })
            },
            {
                cityListView.showDialogError()
            }
        )

    }

    override fun getChosenCity(): String {
        return chosenCityPreferencesImpl.getCity()
    }


    override fun setChosenCity(chosenCityName: String) {
        chosenCityPreferencesImpl.setCity(chosenCityName)
    }


}