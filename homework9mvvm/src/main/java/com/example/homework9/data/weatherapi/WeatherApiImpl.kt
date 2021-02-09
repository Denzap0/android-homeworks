package com.example.homework9mvvm.data.weatherapi

import com.example.homework9mvvm.data.TempUnitType
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody

class WeatherApiImpl() : WeatherAPI {

    private val requestFactory = RequestFactoryImpl()
    private val httpClient = OkHttpClient()
    private val weatherDataListMapper = WeatherDataListMapper()

    override fun getTopHeadLines(coordinatesPair : Pair<Double,Double>, tempUnitType: TempUnitType): Single<List<WeatherDataPresenter>> {
        val weatherDataPresentListMapper = WeatherDataPresenterListMapper(tempUnitType)
        val request = requestFactory.getTopHeadLinesRequest(coordinatesPair.first, coordinatesPair.second)
        return Single.create<String> { emitter ->
            val response = httpClient.newCall(request).execute()
            if(response.isSuccessful){
                if(response.body != null) {
                    emitter.onSuccess((response.body as ResponseBody).string())
                }else{
                    emitter.onError(Throwable("EMPTY RESPONSE"))
                }
            }else{
                emitter.onError(Throwable("API RESPONSE ERROR ${response.code}"))
            }
        }.map { data -> weatherDataListMapper(data) }
            .map { data -> weatherDataPresentListMapper(data) }
            .subscribeOn(Schedulers.io())
    }
}