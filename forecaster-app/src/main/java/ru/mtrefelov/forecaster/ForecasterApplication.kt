package ru.mtrefelov.forecaster

import android.app.Application
import androidx.room.Room
import ru.mtrefelov.forecaster.core.ForecastRepository
import ru.mtrefelov.forecaster.data.DependencyContainer
import ru.mtrefelov.forecaster.data.api.ForecastService
import ru.mtrefelov.forecaster.data.db.ForecastDao
import ru.mtrefelov.forecaster.data.db.ForecastDatabase

class ForecasterApplication : Application(), DependencyContainer {
    private lateinit var repository: ForecastRepository
    private lateinit var dao: ForecastDao

    override fun onCreate() {
        super.onCreate()
        repository = ForecastService(BuildConfig.API_KEY_OPEN_WEATHER)
        dao = Room.databaseBuilder(this, ForecastDatabase::class.java, "forecast-database")
            .build()
            .forecastDao()
    }

    override fun getRepository(): ForecastRepository {
        return repository
    }

    override fun getDao(): ForecastDao {
        return dao
    }
}