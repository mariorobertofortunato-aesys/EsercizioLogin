package com.example.eserciziologin.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eserciziologin.model.Comune
import com.example.eserciziologin.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    // Comuni
    private var listaComuniFromDB = listOf<Comune>()

    private var _listaComuni = MutableLiveData<ArrayList<Comune>>()
    val listaComuni: LiveData<ArrayList<Comune>> get() = _listaComuni

    // Province
    var listaProvince = mutableListOf<String>()
    var provinciaSelected = String()

    // Regioni
    var listaRegioni = mutableListOf<String>()
    var regioneSelected = String()

    init {
        viewModelScope.launch {
            refreshAndLoadComuni()
            getProvince()
            getRegioni()

        }
    }


    /** COMUNI */

    private suspend fun refreshAndLoadComuni() {
        viewModelScope.launch {
            try {
                // Refresh
                mainRepository.refreshComuniInDB()
                // Load (from refreshed DB)
                listaComuniFromDB = mainRepository.getListaComuni()
                _listaComuni.value = ArrayList(listaComuniFromDB)
            } catch (e: Exception) {

            }
        }
    }

    fun getComuneFromDB(comune: String): Comune {
        lateinit var comuneFromBD : Comune
        viewModelScope.launch {
            try {
                val dbQuery = mainRepository.getComune(comune)
                comuneFromBD = dbQuery
            } catch (e: Exception) {
                // TODO
            }
        }
        return comuneFromBD

    }

    fun getSearchedComune(searchString: String) {
        val searchList = mutableListOf<Comune>()

        for (element in listaComuniFromDB ) {
            if ((element.nome).lowercase().startsWith(searchString.lowercase())) {
                searchList.add(element)
            }
        }
        _listaComuni.value = ArrayList(searchList)

    }

    /** PROVINCE */
    private fun getProvince() {

        viewModelScope.launch {
            listaProvince = mainRepository.getProvince()
            Log.d("TAG","$listaProvince")
        }
    }
    fun getComuniFromProvincia() {
        viewModelScope.launch {
            try {
                listaComuniFromDB = mainRepository.getComuniFromProvincia(provinciaSelected)
                _listaComuni.value = ArrayList(listaComuniFromDB)
            } catch (e: Exception) {
            }
        }
    }

    /** REGIONI */
    private fun getRegioni() {

        viewModelScope.launch {
            listaRegioni = mainRepository.getRegioni()
            Log.d("TAG","$listaRegioni")
        }
    }
    fun getComuniFromRegione() {
        viewModelScope.launch {
            try {
                listaComuniFromDB = mainRepository.getComuniFromRegione(regioneSelected)
                _listaComuni.value = ArrayList(listaComuniFromDB)
            } catch (e: Exception) {
            }
        }
    }




}