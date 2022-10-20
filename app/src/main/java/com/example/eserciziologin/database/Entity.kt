package com.example.eserciziologin.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.eserciziologin.model.Comune

@Entity(tableName = "comuni_db")
data class ComuneDB(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "istat") var istat: String,
    @ColumnInfo(name = "zonaNome") var zonaNome: String,
    @ColumnInfo(name = "zonaCodice") var zonaCodice: String,
    @ColumnInfo(name = "regioneNome") var regioneNome: String,
    @ColumnInfo(name = "regioneCodice") var regioneCodice: String,
    @ColumnInfo(name = "provinciaNome") var provinciaNome: String,
    @ColumnInfo(name = "provinciaCodice") var provinciaCodice: String,
    @ColumnInfo(name = "sigla") var sigla: String,
    @ColumnInfo(name = "codiceCatastale") var codiceCatastale: String,
    @ColumnInfo(name = "cap") var cap: String,
    @ColumnInfo(name = "popolazione") var popolazione: Int
)

fun List<ComuneDB>.asDomainModel(): List<Comune> {
    return map {
        Comune(
            id = it.id,
            nome = it.nome,
            istat = it.istat,
            zonaNome = it.zonaNome,
            zonaCodice = it.zonaCodice,
            regioneNome = it.regioneNome,
            regioneCodice = it.regioneCodice,
            provinciaNome = it.provinciaNome,
            provinciaCodice = it.provinciaCodice,
            sigla = it.sigla,
            codiceCatastale = it.codiceCatastale,
            cap = it.cap,
            popolazione = it.popolazione
        )
    }
}

fun ArrayList<Comune>.asDBModel(): List<ComuneDB> {
    return map {
        ComuneDB(
            id = it.id,
            nome = it.nome,
            istat = it.istat,
            zonaNome = it.zonaNome,
            zonaCodice = it.zonaCodice,
            regioneNome = it.regioneNome,
            regioneCodice = it.regioneCodice,
            provinciaNome = it.provinciaNome,
            provinciaCodice = it.provinciaCodice,
            sigla = it.sigla,
            codiceCatastale = it.codiceCatastale,
            cap = it.cap,
            popolazione = it.popolazione
        )
    }
}

fun ComuneDB.asDomainModel(): Comune {
    return Comune(
        id,
        nome,
        istat,
        zonaNome,
        zonaCodice,
        regioneNome,
        regioneCodice,
        provinciaNome,
        provinciaCodice,
        sigla,
        codiceCatastale,
        cap,
        popolazione
    )
}