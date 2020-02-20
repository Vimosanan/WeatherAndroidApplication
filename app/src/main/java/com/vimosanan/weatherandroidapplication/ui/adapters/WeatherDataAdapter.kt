package com.vimosanan.weatherandroidapplication.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vimosanan.weatherandroidapplication.R
import com.vimosanan.weatherandroidapplication.repository.models.WeatherInternalData
import kotlinx.android.synthetic.main.card_view_weather_data.view.*

class WeatherDataAdapter(private var weatherDataSet: MutableList<WeatherInternalData>?): RecyclerView.Adapter<WeatherDataAdapter.WeatherDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDataViewHolder {
        var view: View = LayoutInflater.from(parent.context).inflate(R.layout.card_view_weather_data, parent, false)

        return WeatherDataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return weatherDataSet!!.size
    }

    fun setAdapter(weatherDataSet: MutableList<WeatherInternalData>?){
        this.weatherDataSet = weatherDataSet
        notifyDataSetChanged()
    }

    fun clear(){
        weatherDataSet?.clear()
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: WeatherDataViewHolder, position: Int) {
        weatherDataSet?.let{
            weatherDataSet!![position].let { weatherData ->
                weatherData.date.let {
                    holder.itemView.txtDate.text = it
                }

                weatherData.climate.let {description ->
                   holder.itemView.txtWeather.text = description
                }

                weatherData.type.toLowerCase().let { type ->
                   when (type) {
                       "rain" -> {
                           holder.itemView.imgWeather.setImageResource(R.drawable.rain)
                       }
                       "fog" -> {
                           holder.itemView.imgWeather.setImageResource(R.drawable.fog)
                       }
                       "clear" -> {
                           holder.itemView.imgWeather.setImageResource(R.drawable.clear)
                       }
                       "clouds" -> {
                           holder.itemView.imgWeather.setImageResource(R.drawable.cloudy)
                       }
                       "snow" -> {
                           holder.itemView.imgWeather.setImageResource(R.drawable.snow)
                       }
                       else -> {
                           holder.itemView.imgWeather.setImageResource(R.drawable.clear) //no image for this condition, we have to design for the size and resolution
                       }
                   }
                }

            }
        }

    }

    class WeatherDataViewHolder(dataYearView: View): RecyclerView.ViewHolder(dataYearView)
}