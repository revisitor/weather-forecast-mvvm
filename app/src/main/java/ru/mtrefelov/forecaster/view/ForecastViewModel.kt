package ru.mtrefelov.forecaster.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch

import ru.mtrefelov.forecaster.core.Coordinates
import ru.mtrefelov.forecaster.core.WeatherForecast
import ru.mtrefelov.forecaster.data.api.ForecastRepository

import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: ForecastRepository
) : ViewModel() {

    private val _weather = MutableLiveData<WeatherForecast>()
    val weather: LiveData<WeatherForecast>
        get() = _weather

    private val coordinates = Coordinates(55.5807482, 36.8251487)

    fun fetchWeatherForecast() {
        viewModelScope.launch {
            repository.getWeatherForecast(coordinates).let {
                _weather.postValue(it)
            }
        }
    }
}