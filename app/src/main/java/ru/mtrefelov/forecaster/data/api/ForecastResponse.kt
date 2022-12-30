package ru.mtrefelov.forecaster.data.api

import com.squareup.moshi.Json

class ForecastResponse(
    val city: City,

    @Json(name = "list")
    val details: List<ForecastDetail>
)

class ForecastDetail(
    @Json(name = "dt_txt")
    val timestamp: String,

    @Json(name = "main")
    val temperature: Temperature,
) {
}

class Temperature(
    @Json(name = "temp")
    val value: Double,
)

class City(val name: String)