package com.example.homework9.data.citiesbaseapi

import android.util.Log
import com.example.homework9.presentation.citieslist.CityDataPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CitiesBaseRepositoryImpl(private val cityDao : CityDao) : CitiesBaseRepository {

    private val cityDataPresenterListMapper = CityDataPresenterListMapper()

    override fun readAllCities() : Single<List<CityDataPresenter>> =
        Single.create<List<CityBaseData>>{emitter ->
            emitter.onSuccess(cityDao.readAllCities())
        }.map { list -> cityDataPresenterListMapper(list) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun addCity(city : CityBaseData) : Completable =
        Completable.create {emitter ->
            if(cityDao.getCityData(city.name) == null){
                cityDao.addCity(city)
                emitter.onComplete()
            }else{
                emitter.onError(Throwable("THIS CITY ALREADY EXIST"))
            }

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


    override fun getCity(nameOfCity : String) : Single<CityBaseData> =
        Single.create<CityBaseData> { emitter ->
            val city = cityDao.getCityData(nameOfCity)
            if (city != null) {
                emitter.onSuccess(city)
            }else {
                emitter.onError(Throwable("THERE IS NO CITY WITH THIS NAME"))
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())


}