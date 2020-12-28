package com.example.homework9.data.geocodeapi

import org.json.JSONObject

class GeoCodeMapper : (String) -> Pair<Double, Double> {
    override fun invoke(json: String): Pair<Double, Double> = Pair(
        JSONObject(json).getDouble("latt"),
        JSONObject(json).getDouble("longt")
    )
}