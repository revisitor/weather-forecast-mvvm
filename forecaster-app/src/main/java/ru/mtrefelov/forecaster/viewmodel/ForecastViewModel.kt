package ru.mtrefelov.forecaster.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.mtrefelov.forecaster.core.Coordinates
import ru.mtrefelov.forecaster.core.Forecast
import ru.mtrefelov.forecaster.core.ForecastRepository
import ru.mtrefelov.forecaster.data.db.ForecastDao
import ru.mtrefelov.forecaster.data.toCore
import ru.mtrefelov.forecaster.data.toEntity

class ForecastViewModel(private val repository: ForecastRepository, private val dao: ForecastDao) : ViewModel() {
    class Factory(private val repository: ForecastRepository, private val dao: ForecastDao) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ForecastViewModel(repository, dao) as T
        }
    }

    private val _place = MutableLiveData<String>()
    val place: LiveData<String>
        get() = _place

    private val _forecasts = MutableLiveData<List<Forecast>>()
    val forecasts: LiveData<List<Forecast>>
        get() = _forecasts

    private val coordinates = Coordinates(55.030204, 82.920430)

    suspend fun fetchWeatherForecast() {
        try {
            val response = repository.getWeatherForecast(coordinates)
            response.let {
                _place.postValue(it.place)
                _forecasts.postValue(it.forecasts)
                dao.insertAll(response.forecasts.toEntity())
            }
        } catch (_: Exception) {
            _place.postValue("Novosibirsk (from database)")
            _forecasts.postValue(dao.getAll().toCore())
        }
    }
}