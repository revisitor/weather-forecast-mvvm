package ru.mtrefelov.forecaster.data

import com.squareup.moshi.Json
import ru.mtrefelov.forecaster.core.Forecast
import ru.mtrefelov.forecaster.core.timestampFormat
import java.time.LocalDateTime

class ForecastResponse(
    val city: City,

    @Json(name = "list")
    val details: List<ForecastDetail>
)

class ForecastDetail(
    @Json(name = "dt_txt")
    val timestamp: String,

    @Json(name = "main")
    val temperature: Temperature,
) {
    fun toForecast(): Forecast {
        return Forecast(temperature.value, LocalDateTime.parse(timestamp, timestampFormat))
    }
}

class Temperature(
    @Json(name = "temp")
    val value: Double,
)

class City(val name: String)