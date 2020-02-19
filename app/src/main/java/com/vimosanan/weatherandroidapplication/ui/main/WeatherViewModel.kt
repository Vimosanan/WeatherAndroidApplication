package com.vimosanan.weatherandroidapplication.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vimosanan.weatherandroidapplication.app.Constants
import com.vimosanan.weatherandroidapplication.network.ApiInterface
import com.vimosanan.weatherandroidapplication.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import javax.inject.Inject

class  WeatherViewModel @Inject constructor(var apiInterface: ApiInterface): ViewModel(){

    fun loadData(cityName: String) = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        delay(3000) //intentionally 3 seconds delay to show how loader works

        val response = apiInterface.getWeatherData(cityName, Constants.API_KEY)
        when {
            response.isSuccessful -> {
                emit(Resource.success(response.body()))
            }
            response.code() == 404 -> {
                emit(Resource.error("Results not found!", null))
            }
            else -> {
                emit(Resource.error("Something went wrong!", null))
            }
        }
    }


}
