package ru.mtrefelov.forecaster.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory

import ru.mtrefelov.forecaster.core.*

import timber.log.Timber

private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.HEADERS
    redactHeader("Authorization")
    redactHeader("Cookie")
}

private val client = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .build()

private val moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl("https://api.openweathermap.org/")
    .client(client)
    .build()

class ForecastService(private val apiKey: String) : ForecastRepository {
    private val service = retrofit.create(ForecastApi::class.java)

    override fun getWeatherForecast(coordinates: Coordinates, action: (WeatherForecast) -> Unit) {
        val call = service.getWeatherData(coordinates.latitude, coordinates.longitude, apiKey)
        val callback = makeCallback(action)
        call.enqueue(callback)
    }

    private fun makeCallback(action: (WeatherForecast) -> Unit): Callback<ForecastResponse> {
        return ForecastCallback(action)
    }
}

private class ForecastCallback(
    private val action: (WeatherForecast) -> Unit
) : Callback<ForecastResponse> {

    override fun onResponse(call: Call<ForecastResponse>, response: Response<ForecastResponse>) {
        response.body()?.run {
            val forecasts = details.map { it.toForecast() }
            val city = city.name
            val weatherForecast = WeatherForecast(city, forecasts)
            action(weatherForecast)
        }
    }

    override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
        Timber.e(t)
    }
}