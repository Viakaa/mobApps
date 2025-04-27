package com.example.homework_4.remote

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun validatePhone(number: String) = apiService.validatePhone(number)
}
