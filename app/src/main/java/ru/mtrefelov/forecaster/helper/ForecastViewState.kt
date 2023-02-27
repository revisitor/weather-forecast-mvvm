package ru.mtrefelov.forecaster.helper

import ru.mtrefelov.forecaster.core.WeatherForecast

sealed class ForecastViewState {
    object Clear : ForecastViewState()
    object Loading : ForecastViewState()
    class Loaded(val data: WeatherForecast): ForecastViewState()
}