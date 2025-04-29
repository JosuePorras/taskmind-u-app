package com.moviles.taskmind.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.moviles.taskmind.common.Constants.API_BASE_URL
import com.moviles.taskmind.network.ApiService

//Agregar este import



object RetrofitInstance {
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(API_BASE_URL) // Change to your API URL  //"http://192.168.100.249:5275/"
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}