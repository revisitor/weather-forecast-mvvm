package ru.mtrefelov.forecaster.data.api

import retrofit2.http.*

interface ForecastService {
    @GET("data/2.5/forecast")
    @Headers("Accept: application/json")
    suspend fun getWeatherData(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
    ): ForecastResponse
}