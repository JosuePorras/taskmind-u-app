package com.moviles.taskmind.network

import com.moviles.taskmind.models.HomePageData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomePageApi {
    @GET("api/home-page/getHomePage")
    suspend fun getHomeStatus( @Query("userId") userId: String): Response<HomePageData>
}