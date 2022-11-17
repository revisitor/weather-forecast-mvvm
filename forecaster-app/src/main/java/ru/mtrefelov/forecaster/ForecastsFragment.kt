package ru.mtrefelov.forecaster

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import ru.mtrefelov.forecaster.core.Coordinates
import ru.mtrefelov.forecaster.core.Forecast
import ru.mtrefelov.forecaster.data.ForecastService

class ForecastsFragment : Fragment(R.layout.forecasts_fragment) {
    private lateinit var forecastsAdapter: ForecastsAdapter
    private lateinit var forecastsService: ForecastService

    private var place = ""
    private var forecasts = emptyList<Forecast>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forecastsAdapter = ForecastsAdapter()
        forecastsService = ForecastService(BuildConfig.API_KEY_OPEN_WEATHER)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(activity as AppCompatActivity) {
            val toolbar: Toolbar = view.findViewById(R.id.main_toolbar)
            setSupportActionBar(toolbar)
        }

        view.findViewById<RecyclerView>(R.id.forecasts_recycler_view).apply {
            adapter = forecastsAdapter
            layoutManager = LinearLayoutManager(view.context)
        }

        savedInstanceState?.let(::restoreStateFromBundle) ?: fetchState()
    }

    private fun restoreStateFromBundle(state: Bundle) {
        place = state.getString(KEY_PLACE)!!.also(::setToolbarTitle)
        forecasts = state.getParcelableArrayList<ForecastParcelable>(KEY_FORECASTS)!!
            .map { it.toForecast() }.also(::setForecasts)
    }

    companion object {
        const val KEY_PLACE = "STATE_PLACE"
        const val KEY_FORECASTS = "STATE_FORECASTS"
    }

    private fun fetchState() {
        forecastsService.getWeatherForecast(Coordinates(55.030204, 82.920430)) {
            place = it.place.also(::setToolbarTitle)
            forecasts = it.forecasts.also(::setForecasts)
        }
    }

    private fun setToolbarTitle(title: CharSequence) {
        with(activity as AppCompatActivity) {
            supportActionBar!!.title = title
        }
    }

    private fun setForecasts(forecasts: List<Forecast>) {
        forecastsAdapter.submitList(forecasts)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putCharSequence(KEY_PLACE, place)
            val forecasts = ArrayList(forecasts.map { it.toParcelable() })
            putParcelableArrayList(KEY_FORECASTS, forecasts)
        }
    }
}