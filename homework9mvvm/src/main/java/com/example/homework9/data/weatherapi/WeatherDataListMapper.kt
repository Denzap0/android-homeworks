package com.example.homework9mvvm.data.weatherapi

import com.example.homework9mvvm.data.WeatherData
import org.json.JSONObject
import java.util.*

class WeatherDataListMapper : (String) -> List<WeatherData> {
    override fun invoke(json: String): List<WeatherData> {
        val hoursList = mutableListOf<WeatherData>()
        val articles = JSONObject(json).getJSONArray("hourly")
        for (i in 0 until articles.length()) {
            val jsonObject = articles.getJSONObject(i)
            hoursList.add(
                WeatherData(
                    jsonObject.getString("timezone"),
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