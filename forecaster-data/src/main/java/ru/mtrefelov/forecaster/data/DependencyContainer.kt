package ru.mtrefelov.forecaster.data

import ru.mtrefelov.forecaster.core.ForecastRepository
import ru.mtrefelov.forecaster.data.db.ForecastDao

interface DependencyContainer {
    fun getRepository(): ForecastRepository
    fun getDao(): ForecastDao
}