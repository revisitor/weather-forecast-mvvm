package ru.mtrefelov.forecaster.view

import android.view.View
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import ru.mtrefelov.forecaster.R
import ru.mtrefelov.forecaster.core.*
import ru.mtrefelov.forecaster.databinding.*

sealed class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    protected abstract val datetime: TextView
    protected abstract val temperature: TextView

    open fun bind(forecast: Forecast) {
        datetime.text = forecast.timestamp.format(timestampFormat)
        temperature.text = temperature.resources.getString(R.string.temperature_celsius, forecast.temperatureCelsius)
    }

    class Cold(binding: ColdWeatherItemBinding) : ForecastViewHolder(binding.root) {
        override val datetime: TextView = binding.datetime
        override val temperature: TextView = binding.temperature

        override fun bind(forecast: Forecast) {
            if (forecast.temperatureCelsius > 0) {
                throw IllegalArgumentException("temperature > 0")
            }

            super.bind(forecast)
        }
    }

    class Hot(binding: HotWeatherItemBinding) : ForecastViewHolder(binding.root) {
        override val datetime: TextView = binding.datetime
        override val temperature: TextView = binding.temperature

        override fun bind(forecast: Forecast) {
            if (forecast.temperatureCelsius <= 0) {
                throw IllegalArgumentException("temperature <= 0")
            }

            super.bind(forecast)
        }
    }
}