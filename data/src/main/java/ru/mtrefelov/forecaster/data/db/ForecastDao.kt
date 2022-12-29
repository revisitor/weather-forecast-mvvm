package ru.mtrefelov.forecaster.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {
    @Query("SELECT * FROM forecast")
    suspend fun getAll(): List<ForecastEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(forecasts: List<ForecastEntity>)
}