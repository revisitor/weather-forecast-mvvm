package ru.mtrefelov.forecaster.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.mtrefelov.forecaster.core.Forecast
import ru.mtrefelov.forecaster.helper.ForecastViewState
import ru.mtrefelov.forecaster.viewmodel.ForecastViewModel

@Composable
fun ForecastsScreen(viewModel: ForecastViewModel) {
    val forecastViewState = viewModel.state.observeAsState()
    when (val state = forecastViewState.value) {
        is ForecastViewState.Loading -> ForecastsLoadingView()
        is ForecastViewState.Loaded -> ForecastsLoadedView(
            place = state.data.place,
            forecasts = state.data.forecasts,
        )
        else -> {}
    }
}

@Composable
fun ForecastsLoadingView() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ForecastsLoadedView(place: String, forecasts: List<Forecast>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(place)
                },
            )
        }
    ) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = it)) {
            for (forecast in forecasts) {
                item {
                    if (forecast.temperatureCelsius > 0) {
                        HotWeatherForecastListItem(forecast = forecast)
                    } else {
                        ColdWeatherForecastListItem(forecast = forecast)
                    }
                }
            }
        }
    }
}

