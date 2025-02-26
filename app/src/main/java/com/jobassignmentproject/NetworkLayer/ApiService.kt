package com.jobassignmentproject.NetworkLayer

import com.jobassignmentproject.DataLayer.Gemini.GeminiRequest
import com.jobassignmentproject.DataLayer.Gemini.GeminiResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Authorization: Bearer YOUR_GEMINI_API_KEY")
    @POST("/v1/models/gemini-pro:generateText")
    suspend fun summarizeText(@Body request: GeminiRequest): GeminiResponse
}