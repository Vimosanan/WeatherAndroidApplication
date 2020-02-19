package com.vimosanan.weatherandroidapplication.repository.models

import com.google.gson.annotations.SerializedName

data class WeatherData(
    @SerializedName("weather")
    var weather: List<Weather>? = null,

    @SerializedName("dt_txt")
    var dateAndTime: String? = null
)