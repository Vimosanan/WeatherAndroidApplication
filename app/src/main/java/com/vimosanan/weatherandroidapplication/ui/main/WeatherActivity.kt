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
import com.vimosanan.weatherandroidapplication.R
import com.vimosanan.weatherandroidapplication.repository.models.WeatherData
import com.vimosanan.weatherandroidapplication.ui.adapters.WeatherDataAdapter
import com.vimosanan.weatherandroidapplication.util.Status
import com.vimosanan.weatherandroidapplication.viewmodel.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_weather.*
import javax.inject.Inject

class WeatherActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelProviderFactory: ViewModelProviderFactory

    private lateinit var adapter: WeatherDataAdapter
    private lateinit var viewModel: WeatherViewModel
    private var weatherDataList: MutableList<WeatherData?>? = mutableListOf()


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
    }

    //update the ui with the weather data for the searched query
    private fun updateUI(cityName: String){
        viewModel.loadData(cityName).observe(this, Observer { networkResource ->
            when (networkResource.status) {
                Status.LOADING -> {
                    txtInfo.text = "loading data from network"
                    progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    progressBar.visibility = View.INVISIBLE

                    val entityResponse = networkResource.data

                    if(entityResponse != null) {
                        entityResponse.let {
                            if(it.city != null) {
                                it.city.let{ city ->
                                    if(city!!.name != null) {
                                        txtInfo.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                                        txtInfo.text = "Showing results for ${city.name}"
                                    }
                                }
                            }

                            if(entityResponse.count != 0 && entityResponse.data != null){
                                txtInfo.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
                                adapter.setAdapter(entityResponse.data!!.toMutableList())
                            } else {
                                txtInfo.setTextColor(ContextCompat.getColor(this, R.color.colorRed))
                                txtInfo.text = "Something went wrong!"
                            }
                        }
                    } else {
                        txtInfo.setTextColor(ContextCompat.getColor(this, R.color.colorRed))
                        txtInfo.text = "No Data found for your search result!"
                    }

                    entityResponse
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
    }


    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        var view = activity.currentFocus

        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
