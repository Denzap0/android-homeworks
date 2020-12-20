package com.example.homework9.data.weatherapi

import com.example.homework9.data.TempUnitType
import com.example.homework9.presentation.weatherlist.WeatherDataPresenterListMapper
import com.example.homework9.presentation.weatherlist.WeatherDataPresenter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody

class WeatherApiImpl(tempUnitType: TempUnitType) : WeatherAPI {

    private val requestFactory = RequestFactoryImpl()
    private val httpClient = OkHttpClient()
    private val weatherDataListMapper = WeatherDataListMapper()
    private val weatherDataPresentListMapper = WeatherDataPresenterListMapper(tempUnitType)

    override fun getTopHeadLines(lat : Double, lon : Double): Single<List<WeatherDataPresenter>> {

        val request = requestFactory.getTopHeadLinesRequest(lat, lon)
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
            .subscribeOn(Schedulers.newThread())
            .subscribeOn(AndroidSchedulers.mainThread())
    }
}