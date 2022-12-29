package ru.mtrefelov.forecaster.data

import ru.mtrefelov.forecaster.core.Forecast
import ru.mtrefelov.forecaster.core.WeatherForecast
import ru.mtrefelov.forecaster.core.timestampFormat
import ru.mtrefelov.forecaster.data.api.ForecastDetail
import ru.mtrefelov.forecaster.data.api.ForecastResponse
import ru.mtrefelov.forecaster.data.db.ForecastEntity

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.TimeZone

fun ForecastDetail.toCore(): Forecast {
    return Forecast(temperature.value, LocalDateTime.parse(timestamp, timestampFormat))
}

fun ForecastResponse.toCore(): WeatherForecast {
    val forecasts = details.map { it.toCore() }
    val city = city.name
    return WeatherForecast(city, forecasts)
}

fun ForecastEntity.toCore(): Forecast {
    val time = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(timestamp),
        TimeZone.getDefault().toZoneId()
    )

    return Forecast(temperatureCelsius, time)
}

fun List<ForecastEntity>.toCore(): List<Forecast> {
    return map { it.toCore() }
}

fun List<Forecast>.toEntity(): List<ForecastEntity> {
    return map { it.toEntity() }
}

fun Forecast.toEntity(): ForecastEntity {
    return ForecastEntity(
        0,
        temperatureCelsius,
        timestamp.atZone(ZoneId.systemDefault()).toEpochSecond()
    )
}