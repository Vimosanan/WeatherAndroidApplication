package com.vimosanan.weatherandroidapplication.repository.models

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("coord")
    var location: Location? = null
)