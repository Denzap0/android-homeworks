package com.example.homework9.data.weatherapi

import android.util.Log
import com.example.homework9.data.WeatherData
import org.json.JSONObject
import java.util.Date

class WeatherDataListMapper : (String, String) -> List<WeatherData> {
    override fun invoke(json: String, cityName: String): List<WeatherData> {
        val hoursList = mutableListOf<WeatherData>()
        val articles = JSONObject(json).getJSONArray("hourly")
        for (i in 0 until articles.length()) {
            val jsonObject = articles.getJSONObject(i)
            hoursList.add(
                WeatherData(
                    cityName,
                    Date(jsonObject.getLong("dt") * 1000),
                    jsonObject.getDouble("temp"),
                    jsonObject.getJSONArray("weather").getJSONObject(0).getString("main"),
                    jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon")
                )
            )
        }
        return hoursList
    }

}