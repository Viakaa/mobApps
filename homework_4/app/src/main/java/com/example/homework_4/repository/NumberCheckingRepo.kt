package com.example.homework_4.repository

import com.example.homework_4.model.NumberCheckingDao
import com.example.homework_4.model.NumberChecking
import com.example.homework_4.remote.RemoteDataSource

class NumberCheckingRepo(
    private val remoteDataSource: RemoteDataSource,
    private val localDao: NumberCheckingDao
) {
    suspend fun validatePhone(number: String) = remoteDataSource.validatePhone(number)

    suspend fun saveValidation(entity: NumberChecking) = localDao.insert(entity)

    suspend fun getAllValidations() = localDao.getAll()
}
