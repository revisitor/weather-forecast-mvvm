package ru.mtrefelov.forecaster.core

interface ForecastRepository {
    fun getWeatherForecast(coordinates: Coordinates, action: (WeatherForecast) -> Unit)
}

data class Coordinates(val latitude: Double, val longitude: Double)
