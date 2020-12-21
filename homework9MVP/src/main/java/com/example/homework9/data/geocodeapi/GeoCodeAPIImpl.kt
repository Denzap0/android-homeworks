package com.example.homework9.data.geocodeapi

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.ResponseBody

class GeoCodeAPIImpl : GeoCodeAPI {
    private val requestFactory = RequestFactoryImpl()
    private val httpClient = OkHttpClient()
    private val geoCodeMapper = GeoCodeMapper()
    override fun getTopHeadLines(city: String): Single<Pair<Double, Double>> {
        val request = requestFactory.getTopLinesRequest(city)
        return Single.create<String> { emitter ->
            val response = httpClient.newCall(request).execute()
            if(response.isSuccessful){
                if(response.body != null){
                    emitter.onSuccess((response.body as ResponseBody).string())
                }
            }else{
                emitter.onError(Throwable("API RESPONSE ERROR ${response.code}"))
            }
        }.map { json -> geoCodeMapper(json) }
            .subscribeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
    }
}