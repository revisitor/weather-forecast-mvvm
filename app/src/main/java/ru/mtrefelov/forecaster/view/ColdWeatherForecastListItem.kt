package ru.mtrefelov.forecaster.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.mtrefelov.forecaster.R
import ru.mtrefelov.forecaster.core.Forecast
import ru.mtrefelov.forecaster.core.timestampFormat

val coldWeatherColor = Color(0x30B2EBF2)

@Composable
fun ColdWeatherForecastListItem(forecast: Forecast) {
    Box(modifier = Modifier.background(coldWeatherColor).fillMaxWidth()) {
        Column(modifier = Modifier.padding(all = 8.dp).fillMaxWidth()) {
            Text(
                text = forecast.timestamp.format(timestampFormat),
                modifier = Modifier.fillMaxWidth(),
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                Text(text = stringResource(R.string.temperature_celsius, forecast.temperatureCelsius))
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