package ru.mtrefelov.forecaster.data

import ru.mtrefelov.forecaster.core.Forecast
import ru.mtrefelov.forecaster.core.WeatherForecast
import ru.mtrefelov.forecaster.core.timestampFormat
import ru.mtrefelov.forecaster.data.api.ForecastDetail
import ru.mtrefelov.forecaster.data.api.ForecastResponse
import java.time.LocalDateTime

fun ForecastDetail.toCore(): Forecast {
    return Forecast(temperature.value, LocalDateTime.parse(timestamp, timestampFormat))
}

fun ForecastResponse.toCore(): WeatherForecast {
    val forecasts = details.map { it.toCore() }
    val city = city.name
    return WeatherForecast(city, forecasts)
}
