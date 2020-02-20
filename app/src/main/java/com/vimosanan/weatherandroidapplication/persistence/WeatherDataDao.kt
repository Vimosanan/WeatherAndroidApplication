package com.vimosanan.weatherandroidapplication.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.vimosanan.weatherandroidapplication.repository.models.WeatherInternalData

@Dao
interface WeatherDataDao{

    @Insert(onConflict = IGNORE)
    fun insertWeatherData(dataSet: List<WeatherInternalData>):LongArray

    @Insert(onConflict = IGNORE)
    fun insertSingleData(dataSet: WeatherInternalData)

    @Query("SELECT * FROM weather_data WHERE city_name= :cityName")
    fun getAllWeatherData(cityName: String): List<WeatherInternalData>

    @Query("DELETE FROM weather_data WHERE city_name= :cityName")
    fun deletePreviousData(cityName: String)
}