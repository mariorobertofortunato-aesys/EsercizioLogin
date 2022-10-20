package com.example.eserciziologin.model

data class Comune (
    var id: Int,
    var nome: String,
    var istat: String,
    var zonaNome: String,
    var zonaCodice: String,
    var regioneNome: String,
    var regioneCodice : String,
    var provinciaNome: String,
    var provinciaCodice: String,
    var sigla: String,
    var codiceCatastale: String,
    var cap: String,
    var popolazione: Int
    )