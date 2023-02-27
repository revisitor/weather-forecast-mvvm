package ru.mtrefelov.forecaster.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.mtrefelov.forecaster.core.Coordinates
import ru.mtrefelov.forecaster.data.api.ForecastRepository
import ru.mtrefelov.forecaster.helper.ForecastViewState
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    private val repository: ForecastRepository
) : ViewModel() {

    private val _state = MutableLiveData<ForecastViewState>(ForecastViewState.Clear)
    val state: LiveData<ForecastViewState>
        get() = _state

    private val coordinates = Coordinates(55.5807482, 36.8251487)

    fun fetchWeatherForecast() {
        _state.value = ForecastViewState.Loading
        viewModelScope.launch {
            repository.getWeatherForecast(coordinates).let {
                _state.postValue(ForecastViewState.Loaded(it))
            }
        }
    }
}