package com.example.eserciziologin.repository

import com.example.eserciziologin.database.ComuneDB
import com.example.eserciziologin.database.ComuneRoomDatabase
import javax.inject.Inject

class DBRepository @Inject constructor(private val database: ComuneRoomDatabase) {

    suspend fun getAll(): List<ComuneDB> = database.dao.getAll()

    suspend fun insertAll(comuni: List<ComuneDB>) = database.dao.insertAll(comuni)

    suspend fun getComune(nome: String) = database.dao.getComune(nome)

    suspend fun getProvince(): MutableList<String> = database.dao.getProvince()

    suspend fun getRegioni(): MutableList<String> = database.dao.getRegioni()

    suspend fun getComuniFromProvincia(provinciaSelected: String) = database.dao.getComuniFromProvincia(provinciaSelected)

    suspend fun getComuniFromRegione(regioneSelected: String) = database.dao.getComuniFromRegione(regioneSelected)
}