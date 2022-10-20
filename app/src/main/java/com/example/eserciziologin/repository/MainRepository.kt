package com.example.eserciziologin.repository

import com.example.eserciziologin.database.asDBModel
import com.example.eserciziologin.database.asDomainModel
import com.example.eserciziologin.model.Comune
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class MainRepository @Inject constructor (private val databaseRepository: DBRepository, private val networkRepository: NetworkRepository) {

    suspend fun getListaComuni(): List<Comune> {
        return databaseRepository.getAll().asDomainModel()
    }

    suspend fun getComune(nome: String): Comune {
        return databaseRepository.getComune(nome).asDomainModel()
    }


    suspend fun refreshComuniDB() {
        try {
            val networkRensponse = networkRepository.getAll()
            val comuniFromNetwork = parseComuniJsonResult(networkRensponse)
            databaseRepository.insertAll(comuniFromNetwork.asDBModel())
        } catch (e: Exception) {
        }
    }
}

    fun parseComuniJsonResult(jsonResult: String) : ArrayList<Comune> {

        val comuniList = ArrayList<Comune>()

        val jsonArray = JSONArray(jsonResult)
        for (i in 0 until jsonArray.length()) {
            val mainJsonObject: JSONObject = jsonArray.getJSONObject(i)
            val nome = mainJsonObject.getString("nome")
            val codiceIstat = mainJsonObject.getString("codice")
            val zonaJson = mainJsonObject.getJSONObject("zona")
                val zonaCodice = zonaJson.getString("codice")
                val zonaNome = zonaJson.getString("nome")
            val regioneJson = mainJsonObject.getJSONObject("regione")
                val regioneCodice = regioneJson.getString("codice")
                val regioneNome = regioneJson.getString("nome")
            val provinciaJson = mainJsonObject.getJSONObject("provincia")
                val provinciaCodice = provinciaJson.getString("codice")
                val provinciaNome = provinciaJson.getString("nome")
            val sigla = mainJsonObject.getString("sigla")
            val codiceCatastale = mainJsonObject.getString("codiceCatastale")
            val capArray = mainJsonObject.getJSONArray("cap")
            val capString = capArray.getString(0)
            val popolazione = mainJsonObject.getString("popolazione")


            val comune = Comune(i, nome, codiceIstat,zonaNome,zonaCodice,regioneNome,regioneCodice,provinciaNome,provinciaCodice,sigla,codiceCatastale,capString,popolazione.toInt())
            comuniList.add(comune)
        }

        return comuniList
    }

