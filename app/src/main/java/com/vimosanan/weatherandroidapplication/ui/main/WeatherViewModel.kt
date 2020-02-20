package com.vimosanan.weatherandroidapplication.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.vimosanan.weatherandroidapplication.app.Constants
import com.vimosanan.weatherandroidapplication.network.ApiInterface
import com.vimosanan.weatherandroidapplication.persistence.WeatherDataDao
import com.vimosanan.weatherandroidapplication.repository.models.EntityResponse
import com.vimosanan.weatherandroidapplication.repository.models.WeatherInternalData
import com.vimosanan.weatherandroidapplication.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject

class  WeatherViewModel @Inject constructor(var apiInterface: ApiInterface, var dao: WeatherDataDao): ViewModel(){


    //live data observing the text value for textview_info
    private var _infoStr = MutableLiveData<String> ()
    val infoStr: LiveData<String>
        get() = _infoStr

    //live data observing any change in the list data
    private var _weatherLiveData = MutableLiveData<List<WeatherInternalData>> ()
    val weatherLiveData: LiveData<List<WeatherInternalData>>
        get() = _weatherLiveData

    //live data observing loader value
    private var _loading = MutableLiveData<Boolean> ()
    val loading: LiveData<Boolean>
        get() = _loading

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

    //delete old data before saving new data related to the same query
    private fun deleteSavedData(query: String){

        CoroutineScope(IO).launch {
            dao.deletePreviousData(query.toLowerCase())
        }
    }

    fun convertToInternalObject(entityResponse: EntityResponse){

        val cityName = entityResponse.city?.name
        if(cityName != null){
            val list: MutableList<WeatherInternalData> = mutableListOf()

            CoroutineScope(IO).launch {
                if(cityName != null){
                    for(item in entityResponse.data!!){
                        item.weather!![0].let{
                            val data = WeatherInternalData(null, cityName.toLowerCase(), item.dateAndTime!!, it.type!!, it.description!!)
                            list.add(data)
                        }
                    }
                }
                _infoStr.postValue("Showing results for $cityName") //posting value to the UI thread to observe
                _weatherLiveData.postValue(list)
                saveDataToLocalDatabase(cityName, list)
            }
        } else {
            _infoStr.postValue("Something went wrong!")
        }
    }

    //save data to the local database
    private fun saveDataToLocalDatabase(query: String, list: MutableList<WeatherInternalData>){

        deleteSavedData(query) //delete the old data

        CoroutineScope(IO).launch {
            dao.insertWeatherData(list)
        }
    }

    //load data from database by searching the city name query
    fun getDataFromLocalDatabase(query: String){
        CoroutineScope(IO).launch {
            _infoStr.postValue("Searching for results from local database...")
            _loading.postValue(true)
            delay(3000) //intentionally delay the job for showing the progress bar

            val result = dao.getAllWeatherData(query.toLowerCase())

            _loading.postValue(false)

            if(result.isNotEmpty()){
                _infoStr.postValue("Showing results for $query")
                _weatherLiveData.postValue(result)
            } else {
                _infoStr.postValue("Oops, No result found in OFFLINE mode, switch ON Internet Connection to search!")
            }
        }
    }

}
