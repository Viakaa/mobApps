package com.example.homework_4.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NumberChecking::class], version = 1)
abstract class NumberCheckingDataBase : RoomDatabase() {
    abstract fun numberCheckingDao(): NumberCheckingDao
}
