package com.example.homework9.presentation.citieslist.citylist

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.homework9.data.citiesbaseapi.CitiesBaseRepositoryImpl
import com.example.homework9.data.citiesbaseapi.CitiesRoomDataBase
import com.example.homework9.data.citiesbaseapi.CityBaseData
import com.example.homework9.data.citypreferencesapi.ChosenCityPreferences
import com.example.homework9.data.citypreferencesapi.ChosenCityPreferencesImpl
import com.example.homework9.data.geocodeapi.GeoCodeAPIImpl
import com.example.homework9.view.CityDataView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.function.Function

class CitiesActivityViewModelmpl(
    private val application: Application
) : CitiesActivityViewModel, ViewModel() {

    private val chosenCityPreferencesImpl: ChosenCityPreferences = ChosenCityPreferencesImpl(
        application.getSharedPreferences(
            "chosenCity",
            Context.MODE_PRIVATE
        )
    )
    private var citiesRepository: CitiesBaseRepositoryImpl
    private val cityDataViewMapper = CityDataViewMapper()
    private val geoCodeAPI: GeoCodeAPIImpl = GeoCodeAPIImpl()
    private val citiesMutableLiveData = MutableLiveData<List<CityDataView>>()
    val citiesLiveData: LiveData<List<CityDataView>> = citiesMutableLiveData
    private val isAddCityCompletedMutLiveData = MutableLiveData<Boolean>()
    val isAddCityCompletedMLiveData: LiveData<Boolean> = isAddCityCompletedMutLiveData
    private val chosenCityMutLiveData = MutableLiveData<String>()
    val chosenCityLiveData: LiveData<String> = chosenCityMutLiveData
    private val isAddDoneMutLiveData : MutableLiveData<Boolean> = MutableLiveData()
    val isAddDoneLiveData : LiveData<Boolean> = isAddDoneMutLiveData

    init {
        val cityDao = CitiesRoomDataBase.getInstance(application.baseContext).cityDao()
        citiesRepository = CitiesBaseRepositoryImpl(cityDao)
    }

    override fun fetchCitiesList() {
        citiesRepository.readAllCities().map {list -> cityDataViewMapper(list)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                citiesMutableLiveData.value = list
                chosenCityMutLiveData.value = getChosenCity()
            }, {

            })
    }

    override fun addCity(cityName: String) {
        geoCodeAPI.getCityCode(cityName)
            .flatMap {pair ->
                return@flatMap Single.create<Completable> {emitter -> emitter.onSuccess(citiesRepository.addCity(CityBaseData(null,cityName,pair.first,pair.second)))}
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
                                    citiesMutableLiveData.value = list
                                    chosenCityMutLiveData.value = cityName
                                }
                            isAddDoneMutLiveData.value = true
                        }, {
                            isAddDoneMutLiveData.value = false
                        })
            },
            {
                isAddDoneMutLiveData.value = false
            }
        )
    }

    override fun getChosenCity(): String =
        chosenCityPreferencesImpl.getCity()


    override fun setChosenCity(chosenCityName: String) {
        chosenCityPreferencesImpl.setCity(chosenCityName)
    }


}