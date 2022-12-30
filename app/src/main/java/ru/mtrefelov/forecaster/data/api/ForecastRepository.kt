package ru.mtrefelov.forecaster.data.api

import ru.mtrefelov.forecaster.core.*
import ru.mtrefelov.forecaster.data.toCore
import javax.inject.Inject

class ForecastRepository @Inject constructor(
    private val apiKey: String,
    private val service: ForecastService,
) {
    suspend fun getWeatherForecast(coordinates: Coordinates): WeatherForecast {
        return service.getWeatherData(coordinates.latitude, coordinates.longitude, apiKey).toCore()
    }
}
