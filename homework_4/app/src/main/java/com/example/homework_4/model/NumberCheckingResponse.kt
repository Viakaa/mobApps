package com.example.homework_4.model

data class NumberCheckingResponse(
    val valid: Boolean,
    val country: String,
    val location: String?,
    val country_code: String
)
