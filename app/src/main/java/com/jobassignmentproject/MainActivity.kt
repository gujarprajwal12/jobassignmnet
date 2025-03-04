package com.jobassignmentproject

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.jobassignmentproject.DataLayer.Gemini.GeminiRequest
import com.jobassignmentproject.DataLayer.OpenWeather.WeatherResponse
import com.jobassignmentproject.NetworkLayer.ApiResult
import com.jobassignmentproject.NetworkLayer.ApiService
import com.jobassignmentproject.NetworkLayer.RetrofitInstance
import com.jobassignmentproject.NetworkLayer.safeApiCall
import com.jobassignmentproject.PresentationLayer.utils.NetworkObserver
import com.jobassignmentproject.PresentationLayer.utils.SnackbarUtil
import com.jobassignmentproject.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
  private    val apiKey = "9b36de759e6d947adc9d0f7aba64bdad" // Replace with your actual key

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initview()

        // Set or update the token (can be empty or null)
        RetrofitInstance.setToken("YOUR_DYNAMIC_TOKEN") // Pass null or empty if no token

        fetchWeather("Pune" ,apiKey)
    }





    private fun initview() {

        SnackbarUtil.showShort(binding.root, "Welcome to the app!")

        NetworkObserver.networkStatus.observe(this) { status ->
            if (status == "Slow Internet Connection" || status == "No Internet Connection") {
                SnackbarUtil.showShort(binding.root, status)
            }
        }
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

    override fun onDestroy() {
        super.onDestroy()
        NetworkObserver.unregister()
    }

}