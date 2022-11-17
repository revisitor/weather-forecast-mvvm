package ru.mtrefelov.forecaster.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface ForecastApi {
    @GET("data/2.5/forecast")
    fun getWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
    ): Call<ForecastResponse>
}