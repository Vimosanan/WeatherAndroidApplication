package com.vimosanan.weatherandroidapplication.network

import com.vimosanan.weatherandroidapplication.repository.models.EntityResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    /**
     * REST APIs ACCESS POINT
     * These are suspend functions supported with Coroutine. Retrofit also support suspend function
     * with the new update on >2.6
     */


    /**
     * @param city
     * @param apiKey
     */
    @GET("data/2.5/forecast")
    suspend fun getWeatherData(@Query("q")city: String,
                       @Query("appid")apiKey: String): Response<EntityResponse>
}