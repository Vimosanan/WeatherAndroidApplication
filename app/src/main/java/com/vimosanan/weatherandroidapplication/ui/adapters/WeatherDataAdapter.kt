package com.vimosanan.weatherandroidapplication.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vimosanan.weatherandroidapplication.R
import com.vimosanan.weatherandroidapplication.repository.models.WeatherData
import kotlinx.android.synthetic.main.card_view_weather_data.view.*

class WeatherDataAdapter(private var weatherDataSet: MutableList<WeatherData?>?): RecyclerView.Adapter<WeatherDataAdapter.WeatherDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDataViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_view_weather_data, parent, false)

        return WeatherDataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherDataSet!!.size
    }

    fun setAdapter(weatherDataSet: MutableList<WeatherData?>?){
        this.weatherDataSet = weatherDataSet
        notifyDataSetChanged()
    }

    fun clear(){
        weatherDataSet?.clear()
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

                       weather.type?.toLowerCase().let { type ->
                           if(type == "rain"){
                                holder.itemView.imgWeather.setImageResource(R.drawable.rain)
                           }else if( type == "fog") {
                               holder.itemView.imgWeather.setImageResource(R.drawable.fog)
                           }else if( type == "clear") {
                               holder.itemView.imgWeather.setImageResource(R.drawable.clear)
                           }else if( type == "clouds") {
                               holder.itemView.imgWeather.setImageResource(R.drawable.cloudy)
                           }else if( type == "snow") {
                               holder.itemView.imgWeather.setImageResource(R.drawable.snow)
                           }else{
                               holder.itemView.imgWeather.setImageResource(R.drawable.clear) //no image for this condition, we have to design for the size and resolution
                           }
                       }
                   }
               }

            }
        }

    }

    class WeatherDataViewHolder(dataYearView: View): RecyclerView.ViewHolder(dataYearView)
}