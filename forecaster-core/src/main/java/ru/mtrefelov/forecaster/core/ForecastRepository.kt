package ru.mtrefelov.forecaster.core

interface ForecastRepository {
    suspend fun getWeatherForecast(coordinates: Coordinates): WeatherForecast
}

data class Coordinates(val latitude: Double, val longitude: Double)
