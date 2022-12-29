package ru.mtrefelov.forecaster.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.coroutines.launch

import ru.mtrefelov.forecaster.core.Forecast
import ru.mtrefelov.forecaster.data.DependencyContainer
import ru.mtrefelov.forecaster.databinding.ForecastsFragmentBinding
import ru.mtrefelov.forecaster.viewmodel.ForecastViewModel

class ForecastsFragment : Fragment() {
    private lateinit var forecastsAdapter: ForecastsAdapter
    private lateinit var viewModel: ForecastViewModel

    private var _binding: ForecastsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forecastsAdapter = ForecastsAdapter()
        viewModel = createViewModel()
    }

    private fun createViewModel(): ForecastViewModel {
        val container = requireActivity().application as DependencyContainer
        val factory = ForecastViewModel.Factory(container.getRepository(), container.getDao())
        return ViewModelProvider(this, factory)[ForecastViewModel::class.java]
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

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.forecasts.apply {
            adapter = forecastsAdapter
            layoutManager = LinearLayoutManager(view.context)
        }

        viewModel.weather.observe(viewLifecycleOwner) {
            setToolbarTitle(it.place)
            setForecasts(it.forecasts)
        }

        if (savedInstanceState == null) {
            viewModel.viewModelScope.launch {
                viewModel.fetchWeatherForecast()
            }
        }
    }

    private fun setToolbarTitle(title: CharSequence) {
        (activity as AppCompatActivity)
            .supportActionBar!!
            .title = title
    }

    private fun setForecasts(forecasts: List<Forecast>) {
        forecastsAdapter.submitList(forecasts)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}