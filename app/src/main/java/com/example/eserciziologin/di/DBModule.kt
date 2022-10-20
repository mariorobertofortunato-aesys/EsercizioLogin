package com.example.eserciziologin.di

import android.content.Context
import androidx.room.Room
import com.example.eserciziologin.database.ComuneRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): ComuneRoomDatabase = Room.
    databaseBuilder(
        context.applicationContext,
        ComuneRoomDatabase::class.java,
        "comuni_db"
    ).build()
}