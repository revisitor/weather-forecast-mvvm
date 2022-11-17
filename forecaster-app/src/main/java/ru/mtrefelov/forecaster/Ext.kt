package ru.mtrefelov.forecaster

import ru.mtrefelov.forecaster.core.Forecast
import java.time.*
import java.util.*

fun Forecast.toParcelable(): ForecastParcelable {
    val seconds = timestamp.toInstant(ZoneOffset.UTC).epochSecond
    return ForecastParcelable(temperatureCelsius, seconds)
}

fun ForecastParcelable.toForecast(): Forecast {
    val timestamp = LocalDateTime.ofInstant(
        Instant.ofEpochSecond(timestampSeconds), TimeZone.getDefault().toZoneId()
    )

    return Forecast(temperatureCelsius, timestamp)
}