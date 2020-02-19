package com.vimosanan.weatherandroidapplication.repository.models

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("id")
    var id: Int? = null,

    @SerializedName("main")
    var type: String? = null,

    @SerializedName("description")
    var description: String? = null,

    @SerializedName("icon")
    var imgName: String? = null
)