package com.vimosanan.weatherandroidapplication.repository.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weather_data")
data class WeatherInternalData (

    @field:PrimaryKey(autoGenerate = true)
    val id: Int?,

    @field:ColumnInfo(name = "city_name")
    val cityName: String,

    @field:ColumnInfo(name = "forecast_date")
    val date: String,

    @field:ColumnInfo(name = "weather_type")
    val type: String,

    @field:ColumnInfo(name = "climate_condition")
    val climate: String
)