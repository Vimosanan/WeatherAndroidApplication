package com.vimosanan.weatherandroidapplication.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vimosanan.weatherandroidapplication.R
import dagger.android.support.DaggerAppCompatActivity

class WeatherActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
    }
}
