package com.example.eserciziologin.repository

import com.example.eserciziologin.network.ApiService
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val apiServiceComuni: ApiService) {

    suspend fun getAll(): String = apiServiceComuni.getAll()

}