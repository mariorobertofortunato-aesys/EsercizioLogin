package com.example.eserciziologin.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {

    @Query("SELECT * FROM comuni_db ORDER BY id")
    suspend fun getAll() : List<ComuneDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(comuni: List<ComuneDB>)

    @Query("SELECT * FROM comuni_db WHERE nome = (:nome)")
    suspend fun getComune(nome: String) : ComuneDB

}