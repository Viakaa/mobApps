package com.example.homework_4.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "number_checking_table")
data class NumberChecking(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val number: String,
    val valid: Boolean,
    val country: String,
    val location: String?,
    val country_code: String
)
