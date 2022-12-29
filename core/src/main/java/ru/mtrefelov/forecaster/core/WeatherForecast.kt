package ru.mtrefelov.forecaster.core

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val timestampFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

data class WeatherForecast(
    val place: String,
    val forecasts: List<Forecast>
)

data class Forecast(
    val temperatureCelsius: Double,
    val timestamp: LocalDateTime
)