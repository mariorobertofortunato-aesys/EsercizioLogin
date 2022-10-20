package com.example.eserciziologin.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ComuneDB::class], version = 1, exportSchema = false)
abstract class ComuneRoomDatabase : RoomDatabase() {

    abstract val dao: Dao

}