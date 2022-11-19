package ru.mtrefelov.forecaster.viewmodel

import androidx.lifecycle.*

import ru.mtrefelov.forecaster.BuildConfig
import ru.mtrefelov.forecaster.core.*
import ru.mtrefelov.forecaster.data.ForecastService

class ForecastViewModel : ViewModel() {
    private val _place = MutableLiveData<String>()
    val place: LiveData<String>
        get() = _place

    private val _forecasts = MutableLiveData<List<Forecast>>()
    val forecasts: LiveData<List<Forecast>>
        get() = _forecasts

    private val coordinates = Coordinates(55.030204, 82.920430)

    private val service: ForecastRepository = ForecastService(BuildConfig.API_KEY_OPEN_WEATHER)

    fun fetchWeatherForecast() {
        service.getWeatherForecast(coordinates) {
            _place.value = it.place
            _forecasts.value = it.forecasts
        }
    }
}