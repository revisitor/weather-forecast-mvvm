package ru.mtrefelov.forecaster.view

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import ru.mtrefelov.forecaster.core.Forecast

@Composable
fun ForecastsScreen(viewModel: ForecastViewModel) {
    val weatherState = viewModel.weather.observeAsState()

    Scaffold(
        topBar = {
            ForecastsToolbar(place = weatherState.value?.place ?: "")
        }
    ) {
        ForecastsListView(
            forecasts = weatherState.value?.forecasts ?: emptyList(),
            padding = it
        )
    }
}

@Composable
fun ForecastsToolbar(place: String) {
    TopAppBar(
        title = {
            Text(place)
        },
    )
}

@Composable
fun ForecastsListView(forecasts: List<Forecast>, padding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        for (forecast in forecasts) {
            item {
                if (forecast.temperatureCelsius > 0) {
                    WeatherHot(forecast = forecast)
                } else {
                    WeatherCold(forecast = forecast)
                }
            }
        }
    }
}