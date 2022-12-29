package ru.mtrefelov.forecaster.view

import android.view.*
import androidx.recyclerview.widget.*

import ru.mtrefelov.forecaster.core.Forecast
import ru.mtrefelov.forecaster.databinding.*

class ForecastsAdapter : ListAdapter<Forecast, ForecastViewHolder>(ItemCallback()) {
    private class ItemCallback : DiffUtil.ItemCallback<Forecast>() {
        override fun areItemsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Forecast, newItem: Forecast): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == HOT_WEATHER_TYPE) {
            val binding = HotWeatherItemBinding.inflate(inflater, parent, false)
            ForecastViewHolder.Hot(binding)
        } else {
            val binding = ColdWeatherItemBinding.inflate(inflater, parent, false)
            ForecastViewHolder.Cold(binding)
        }
    }

    companion object {
        private const val HOT_WEATHER_TYPE = 0
        private const val COLD_WEATHER_TYPE = 1
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        val forecast = getItem(position)
        return if (forecast.temperatureCelsius > 0) HOT_WEATHER_TYPE else COLD_WEATHER_TYPE
    }
}