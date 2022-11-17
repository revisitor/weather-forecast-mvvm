package ru.mtrefelov.forecaster

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.mtrefelov.forecaster.core.Forecast

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
        return LayoutInflater.from(parent.context).run {
            if (viewType == HOT_WEATHER_TYPE) {
                val root = inflate(R.layout.hot_weather_item, parent, false)
                ForecastViewHolder.Hot(root)
            } else {
                val root = inflate(R.layout.cold_weather_item, parent, false)
                ForecastViewHolder.Cold(root)
            }
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