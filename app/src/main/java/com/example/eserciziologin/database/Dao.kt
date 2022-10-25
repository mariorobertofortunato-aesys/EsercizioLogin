package com.example.eserciziologin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {

    // Comuni
    @Query("SELECT * FROM comuni_db ORDER BY id")
    suspend fun getAll() : List<ComuneDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comuni: List<ComuneDB>)

    @Query("SELECT * FROM comuni_db WHERE nome = (:nome)")
    suspend fun getComune(nome: String) : ComuneDB

    @Query ("SELECT * FROM comuni_db WHERE provinciaNome = (:provinciaSelected)")
    suspend fun getComuniFromProvincia(provinciaSelected: String): List<ComuneDB>

    @Query ("SELECT * FROM comuni_db WHERE regioneNome = (:regioneSelected)")
    suspend fun getComuniFromRegione(regioneSelected: String): List<ComuneDB>


    // Province
    @Query("SELECT DISTINCT provinciaNome FROM comuni_db ORDER BY provinciaNome ASC")
    suspend fun getProvince() : List<String>

    @Query("SELECT DISTINCT provinciaNome FROM comuni_db WHERE regioneNome = (:regioneSelected) ORDER BY provinciaNome ASC")
    suspend fun getProvinceFromRegione(regioneSelected: String): List<String>


    // Regioni
    @Query("SELECT DISTINCT regioneNome FROM comuni_db ORDER BY regioneNome ASC")
    suspend fun getRegioni() : List<String>



}