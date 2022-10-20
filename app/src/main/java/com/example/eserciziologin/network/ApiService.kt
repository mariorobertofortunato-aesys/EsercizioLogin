package com.example.eserciziologin.network

import retrofit2.http.GET

interface ApiService {

    @GET("comuni.json")
    suspend fun getAll(): String

}