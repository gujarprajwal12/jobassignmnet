package com.jobassignmentproject.DataLayer.OpenWeather


data class WeatherResponse(
    val name: String, // City name
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double,
    val humidity: Int
)

data class Weather(
    val description: String
)

