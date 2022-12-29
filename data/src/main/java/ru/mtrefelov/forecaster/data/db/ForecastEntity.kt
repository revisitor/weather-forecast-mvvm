package ru.mtrefelov.forecaster.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "temperature") val temperatureCelsius: Double,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
)
