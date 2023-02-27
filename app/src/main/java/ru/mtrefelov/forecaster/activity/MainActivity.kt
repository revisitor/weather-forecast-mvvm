package ru.mtrefelov.forecaster.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import dagger.hilt.android.AndroidEntryPoint
import ru.mtrefelov.forecaster.view.ForecastsScreen
import ru.mtrefelov.forecaster.viewmodel.ForecastViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: ForecastViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                ForecastsScreen(viewModel)
            }
        }

        if (savedInstanceState == null) {
            viewModel.fetchWeatherForecast()
        }
    }
}