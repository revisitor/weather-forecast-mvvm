package ru.mtrefelov.forecaster.data

import ru.mtrefelov.forecaster.core.WeatherForecast

fun ForecastResponse.toCore(): WeatherForecast {
    val forecasts = details.map { it.toForecast() }
    val city = city.name
    return WeatherForecast(city, forecasts)
}