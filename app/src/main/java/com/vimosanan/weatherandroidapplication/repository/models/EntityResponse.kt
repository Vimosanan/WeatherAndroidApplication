package com.vimosanan.weatherandroidapplication.repository.models

import com.google.gson.annotations.SerializedName

data class EntityResponse(
    @SerializedName("cod")
    var code: Int? = null,

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("cnt")
    var count: Int? = null,

    @SerializedName("list")
    var data: List<WeatherData>? = null,

    @SerializedName("city")
    var city: City? = null
)