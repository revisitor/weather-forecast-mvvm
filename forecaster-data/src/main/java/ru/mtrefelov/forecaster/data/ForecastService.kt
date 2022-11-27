package ru.mtrefelov.forecaster.data

import ru.mtrefelov.forecaster.core.Coordinates
import ru.mtrefelov.forecaster.core.ForecastRepository
import ru.mtrefelov.forecaster.core.WeatherForecast

class ForecastService(private val apiKey: String) : ForecastRepository {
    private val service = retrofit.create(ForecastApi::class.java)

    override suspend fun getWeatherForecast(coordinates: Coordinates): WeatherForecast {
        return service.getWeatherData(coordinates.latitude, coordinates.longitude, apiKey).toCore()
    }
}
