package com.example.homework_4.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.homework_4.model.NumberChecking
import kotlinx.coroutines.flow.Flow

@Dao
interface NumberCheckingDao {

    @Insert
    suspend fun insert(validation: NumberChecking): Long

    @Query("SELECT * FROM number_checking_table")
    fun getAll(): Flow<List<NumberChecking>>
}
