package com.example.movies.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CategoryMovie {

    @GET("/")
    suspend fun searchByName(@Query("s") query: String, @Query("apikey") apiKey: String): Response<MovieRespond>


    @GET("/")
    suspend fun findById(@Query("i") query: String, @Query("apikey") apiKey: String) : Response<New_item>

}