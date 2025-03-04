package com.jobassignmentproject.PresentationLayer.Ui.OpenWeather

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.jobassignmentproject.DataLayer.OpenWeather.WeatherResponse
import com.jobassignmentproject.NetworkLayer.ApiResult
import com.jobassignmentproject.NetworkLayer.RetrofitInstance
import com.jobassignmentproject.NetworkLayer.safeApiCall
import com.jobassignmentproject.R
import com.jobassignmentproject.databinding.ActivityOpenWeatherSecrrenBinding
import kotlinx.coroutines.launch

class OpenWeatherSecrren : AppCompatActivity() {

    private lateinit var binding: ActivityOpenWeatherSecrrenBinding

    private    val apiKey = "9b36de759e6d947adc9d0f7aba64bdad"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOpenWeatherSecrrenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        OpenWeatherSecrrenApiCall()

    }

    private fun OpenWeatherSecrrenApiCall() {

        fetchWeather("Pune" ,apiKey)
    }

    private fun fetchWeather(city: String , apikeyopenweather: String) {
        lifecycleScope.launch {
            val response = safeApiCall {
                RetrofitInstance.api.getCurrentWeather(city, apikeyopenweather)
            }

            when (response) {
                is ApiResult.Success -> showWeather(response.data)
                is ApiResult.Error -> showError(response.message)
            }
        }
    }

    private fun showWeather(weather: WeatherResponse) {
        val message = "City: ${weather.name}, Temp: ${weather.main.temp}°C"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        Log.d(TAG, "showWeather: $message")
    }

    private fun showError(message: String) {
        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()
    }
}