package ru.mtrefelov.forecaster.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mtrefelov.forecaster.core.Coordinates
import ru.mtrefelov.forecaster.core.ForecastRepository
import ru.mtrefelov.forecaster.core.WeatherForecast
import ru.mtrefelov.forecaster.data.db.ForecastDao
import ru.mtrefelov.forecaster.data.toEntity

class ForecastViewModel(private val repository: ForecastRepository, private val dao: ForecastDao) :
    ViewModel() {
    class Factory(private val repository: ForecastRepository, private val dao: ForecastDao) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return ForecastViewModel(repository, dao) as T
        }
    }

    private val _weather = MutableLiveData<WeatherForecast>()
    val weather: LiveData<WeatherForecast>
        get() = _weather

    private val coordinates = Coordinates(55.030204, 82.920430)

    suspend fun fetchWeatherForecast() {
        repository.getWeatherForecast(coordinates).let {
            _weather.postValue(it)
            dao.insertAll(it.forecasts.toEntity())
        }
    }
}