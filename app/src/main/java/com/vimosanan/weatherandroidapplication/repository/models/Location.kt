package com.vimosanan.weatherandroidapplication.repository.models

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("lon")
    var longitude: Float? = null,

    @SerializedName("lat")
    var latitude: Float? = null
)