package ru.mtrefelov.forecaster.view

import android.os.Bundle
import android.view.*

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager

import ru.mtrefelov.forecaster.core.Forecast
import ru.mtrefelov.forecaster.databinding.ForecastsFragmentBinding
import ru.mtrefelov.forecaster.viewmodel.ForecastViewModel

class ForecastsFragment : Fragment() {
    private lateinit var forecastsAdapter: ForecastsAdapter

    private var _binding: ForecastsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ForecastViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forecastsAdapter = ForecastsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ForecastsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity)
            .setSupportActionBar(binding.toolbar)

        binding.forecasts.apply {
            adapter = forecastsAdapter
            layoutManager = LinearLayoutManager(view.context)
        }

        with(viewModel) {
            if (savedInstanceState == null) {
                fetchWeatherForecast {
                    setToolbarTitle(it.place)
                    setForecasts(it.forecasts)
                }
            } else {
                setToolbarTitle(place)
                setForecasts(forecasts)
            }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}