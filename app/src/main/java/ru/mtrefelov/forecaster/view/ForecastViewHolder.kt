package ru.mtrefelov.forecaster.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mtrefelov.forecaster.R
import ru.mtrefelov.forecaster.core.Forecast
import ru.mtrefelov.forecaster.core.timestampFormat

@Composable
fun WeatherCold(forecast: Forecast) {
    Box(
        modifier = Modifier
            .background(colorResource(R.color.cold_weather_color))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = forecast.timestamp.format(timestampFormat),
                modifier = Modifier.fillMaxWidth(),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                Text(
                    stringResource(R.string.temperature_celsius, forecast.temperatureCelsius),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    painter = painterResource(R.drawable.cold_weather_item_icon),
                    contentDescription = stringResource(R.string.cold_weather_icon_description),
                    modifier = Modifier.padding(vertical = 4.dp),
                )
                Spacer(modifier = Modifier.width(32.dp))
            }
        }
    }
}

@Composable
fun WeatherHot(forecast: Forecast) {
    Box(
        modifier = Modifier
            .background(colorResource(R.color.hot_weather_color))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = forecast.timestamp.format(timestampFormat),
                modifier = Modifier.fillMaxWidth(),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                Image(
                    painter = painterResource(R.drawable.hot_weather_item_icon),
                    contentDescription = stringResource(R.string.hot_weather_icon_description),
                    modifier = Modifier.size(width = 25.dp, height = 25.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    stringResource(R.string.temperature_celsius, forecast.temperatureCelsius),
                )
                Spacer(modifier = Modifier.width(32.dp))
            }
        }
    }
}