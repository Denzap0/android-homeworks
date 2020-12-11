package com.example.homework9.WeatherAPI

import com.example.homework9.Model.WeatherData
import com.example.homework9.Model.WeatherDataMapper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody

class WeatherApiImpl() : WeatherAPI {

    private val requestFactory = RequestFactoryImpl()
    private val httpClient = OkHttpClient()
    private val weatherDataMapper = WeatherDataMapper()

    override fun getTopHeadLines(city: String): Single<List<WeatherData>> {

        val request = requestFactory.getTopHeadLinesRequest(city)
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
        }.map { data -> weatherDataMapper(data) }
            .subscribeOn(Schedulers.newThread())
            .subscribeOn(AndroidSchedulers.mainThread())
    }
}