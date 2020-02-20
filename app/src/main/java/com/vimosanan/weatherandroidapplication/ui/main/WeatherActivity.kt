package com.vimosanan.weatherandroidapplication.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.vimosanan.weatherandroidapplication.R
import com.vimosanan.weatherandroidapplication.persistence.WeatherDataDatabase
import com.vimosanan.weatherandroidapplication.repository.models.EntityResponse
import com.vimosanan.weatherandroidapplication.repository.models.WeatherInternalData
import com.vimosanan.weatherandroidapplication.ui.adapters.WeatherDataAdapter
import com.vimosanan.weatherandroidapplication.util.NetworkStatus
import com.vimosanan.weatherandroidapplication.util.Status
import com.vimosanan.weatherandroidapplication.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var adapter: WeatherDataAdapter
    private lateinit var viewModel: WeatherViewModel
    private var weatherDataList: MutableList<WeatherInternalData>? = mutableListOf()


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        viewModel = ViewModelProviders
            .of(this, viewModelProviderFactory)
            .get(WeatherViewModel::class.java)

        adapter = WeatherDataAdapter(weatherDataList)

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager?
        recyclerView.adapter = adapter

        btnSearch.setOnClickListener{
            val searchQuery = edtTxtSearch.text.toString()
            txtInfo.setTextColor(ContextCompat.getColor(this, R.color.colorGreyDark))
            adapter.clear()

            if(searchQuery != ""){
                hideKeyboard(this)
                updateUI(searchQuery)
            } else {
                txtInfo.text = "No Results Found!"
                Toast.makeText(this, "Search any value!", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.infoStr.observe(this, Observer {
            it?.let {
                txtInfo.text = it
            }
        })

        viewModel.weatherLiveData.observe(this, Observer {
            if(it != null){
                if(it.isNotEmpty()){
                    adapter.setAdapter(it.toMutableList())
                }
            }
        })

        viewModel.loading.observe(this, Observer {
            if(it){
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
            }
        })
    }

    //update the ui with the weather data for the searched query
    private fun updateUI(cityName: String){
        if(NetworkStatus.isNetworkConnected(this)){
            viewModel.loadData(cityName).observe(this, Observer { networkResource ->
                when (networkResource.status) {
                    Status.LOADING -> {
                        txtInfo.text = "loading data from network"
                        progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        progressBar.visibility = View.INVISIBLE

                        val entityResponse = networkResource.data

                        //<<<<<<<<<<SAVE TO DATABASE>>>>>>>>>>>>>>>>>\\
                        viewModel.convertToInternalObject(entityResponse!!)
                    }

                    Status.ERROR -> {
                        adapter.clear()
                        val message = networkResource.msg
                        progressBar.visibility = View.INVISIBLE

                        txtInfo.setTextColor(ContextCompat.getColor(this, R.color.colorRed))
                        message?.let{
                            txtInfo.text = it
                        }
                    }
                }
            })

        } else {
            viewModel.getDataFromLocalDatabase(cityName) //in case there is no internet connection search in local database
        }
    }

    //hide the input soft keyboard
    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        var view = activity.currentFocus

        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
