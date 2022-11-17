package ru.mtrefelov.forecaster.viewmodel

import androidx.lifecycle.ViewModel

import ru.mtrefelov.forecaster.BuildConfig
import ru.mtrefelov.forecaster.core.Coordinates
import ru.mtrefelov.forecaster.core.Forecast
import ru.mtrefelov.forecaster.core.WeatherForecast
import ru.mtrefelov.forecaster.data.ForecastService

class ForecastViewModel : ViewModel() {
    private lateinit var _place: String
    val place get() = _place

    private lateinit var _forecasts: List<Forecast>
    val forecasts get() = _forecasts.toList()

    private val coordinates = Coordinates(55.030204, 82.920430)

    private val service = ForecastService(BuildConfig.API_KEY_OPEN_WEATHER)

    fun fetchWeatherForecast(action: (WeatherForecast) -> Unit) {
        service.getWeatherForecast(coordinates) {
            if (!::_place.isInitialized) _place = it.place
            if (!::_forecasts.isInitialized) _forecasts = it.forecasts
            action(it)
        }
    }
}