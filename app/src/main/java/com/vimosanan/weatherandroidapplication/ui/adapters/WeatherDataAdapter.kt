package com.vimosanan.weatherandroidapplication.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vimosanan.weatherandroidapplication.R
import com.vimosanan.weatherandroidapplication.repository.models.WeatherData
import kotlinx.android.synthetic.main.card_view_weather_data.view.*

class WeatherDataAdapter(private var weatherDataSet: List<WeatherData?>?): RecyclerView.Adapter<WeatherDataAdapter.WeatherDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDataViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_view_weather_data, parent, false)

        return WeatherDataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherDataSet!!.size
    }

    fun setAdapter(weatherDataSet: List<WeatherData>){
        this.weatherDataSet = weatherDataSet
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: WeatherDataViewHolder, position: Int) {
        weatherDataSet?.let{
            weatherDataSet!![position]?.let { weatherData ->
                weatherData.dateAndTime?.let {
                    holder.itemView.txtDate.text = it
                }

               weatherData.weather?.let{
                   it[0]?.let {weather ->
                       weather.description?.let {description ->
                           holder.itemView.txtWeather.text = description
                       }

                       weather.type?.let { type ->
                           if(type == ""){
                               //set image accordingly
                           }
                       }
                   }
               }

            }
        }

    }

    class WeatherDataViewHolder(dataYearView: View): RecyclerView.ViewHolder(dataYearView)
}