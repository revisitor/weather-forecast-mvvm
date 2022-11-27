package ru.mtrefelov.forecaster.data.api

import ru.mtrefelov.forecaster.core.*
import ru.mtrefelov.forecaster.data.toCore

class ForecastService(private val apiKey: String) : ForecastRepository {
    private val service = retrofit.create(ForecastApi::class.java)

    override suspend fun getWeatherForecast(coordinates: Coordinates): WeatherForecast {
        return service.getWeatherData(coordinates.latitude, coordinates.longitude, apiKey).toCore()
    }
}
