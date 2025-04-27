package com.example.homework_4.remote

import com.example.homework_4.model.NumberCheckingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers(
        "X-RapidAPI-Key: fc6158fadfmsh11b65f764c0a3e6p1e42c2jsn55091189707b",
        "X-RapidAPI-Host: validate-phone-by-api-ninjas.p.rapidapi.com"
    )
    @GET("v1/validatephone")
    suspend fun validatePhone(@Query("number") number: String): Response<NumberCheckingResponse>
}