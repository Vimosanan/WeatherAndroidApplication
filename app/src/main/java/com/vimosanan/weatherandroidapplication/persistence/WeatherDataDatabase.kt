package com.vimosanan.weatherandroidapplication.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vimosanan.weatherandroidapplication.repository.models.WeatherInternalData


@Database(entities = [WeatherInternalData::class], version = 2 ,exportSchema = false)
abstract class WeatherDataDatabase: RoomDatabase() {
    abstract fun weatherDataDao(): WeatherDataDao
}