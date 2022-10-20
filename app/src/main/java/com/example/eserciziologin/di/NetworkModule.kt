package com.example.eserciziologin.di

import com.example.eserciziologin.network.ApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

const val BASE_URL_COMUNI = "https://raw.githubusercontent.com/matteocontrini/comuni-json/master/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL_COMUNI)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder()
                    .setLenient()
                    .create()))
        .build().create(ApiService::class.java)

}