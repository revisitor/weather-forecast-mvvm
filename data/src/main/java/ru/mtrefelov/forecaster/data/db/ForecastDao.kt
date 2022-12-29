package ru.mtrefelov.forecaster.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {
    @Query("SELECT * FROM forecast")
    fun getAll(): List<ForecastEntity>

    @Query("SELECT * FROM forecast WHERE timestamp = :timestamp")
    fun findByTimestamp(timestamp: Long): ForecastEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(forecasts: List<ForecastEntity>)
}