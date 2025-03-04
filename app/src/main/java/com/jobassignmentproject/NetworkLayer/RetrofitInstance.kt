package com.jobassignmentproject.NetworkLayer


import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private var token: String? = null // Token can be nullable

    // Function to set or update the token
    fun setToken(newToken: String?) {
        token = newToken
    }

    // OkHttpClient with Interceptor that checks token every time
    private val client: OkHttpClient
        get() = OkHttpClient.Builder().apply {
            addInterceptor(Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()

                // Only add Authorization header if the token is not empty
                if (!token.isNullOrEmpty()) {
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }

                chain.proceed(requestBuilder.build())
            })
        }.build()

    // Retrofit instance (rebuilds if client changes)
    val api: ApiService
        get() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // Always uses the latest client with the latest token
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
}
