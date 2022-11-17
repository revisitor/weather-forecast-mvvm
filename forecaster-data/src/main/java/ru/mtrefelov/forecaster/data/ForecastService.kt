package ru.mtrefelov.forecaster.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

import ru.mtrefelov.forecaster.core.WeatherForecast
import ru.mtrefelov.forecaster.core.Coordinates
import ru.mtrefelov.forecaster.core.ForecastRepository

import timber.log.Timber

private val loggingInterceptor = Interceptor { chain ->
    val original = chain.request()
    val request = original.newBuilder()
        .header("Accept", "application/json")
        .method(original.method, original.body)
        .build()

    val sendingTimeMs = System.currentTimeMillis()
    Timber.d("Sending request ${request.url.host}")

    val response = chain.proceed(request)
    val duration = System.currentTimeMillis() - sendingTimeMs
    Timber.d("Received response for ${response.request.url.host} in $duration ms")

    response
}

private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.HEADERS
    redactHeader("Authorization")
    redactHeader("Cookie")
}

private val client = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor)
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

private class ForecastCallback(private val action: (WeatherForecast) -> Unit) : Callback<ForecastResponse> {
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