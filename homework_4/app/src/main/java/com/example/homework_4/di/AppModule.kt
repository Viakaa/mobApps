package com.example.homework_4.di

import android.app.Application
import androidx.room.Room
import com.example.homework_4.model.NumberCheckingDataBase
import com.example.homework_4.remote.ApiService
import com.example.homework_4.remote.RemoteDataSource
import com.example.homework_4.repository.NumberCheckingRepo
import com.example.homework_4.viewmodel.NumberCheckingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://validate-phone-by-api-ninjas.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    single { RemoteDataSource(get()) }

    single {
        Room.databaseBuilder(get<Application>(), NumberCheckingDataBase::class.java, "validation_db")
            .build()
    }

    single { get<NumberCheckingDataBase>().numberCheckingDao() }

    single { NumberCheckingRepo(get(), get()) }

    viewModel { NumberCheckingViewModel(get()) }
}
