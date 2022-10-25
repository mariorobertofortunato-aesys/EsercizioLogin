package com.example.eserciziologin.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eserciziologin.MyApp
import com.example.eserciziologin.model.Comune
import com.example.eserciziologin.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    // Comuni
    var listaComuniFromDB = listOf<Comune>()

    private var _listaComuni = MutableLiveData<ArrayList<Comune>>()
    val listaComuni: LiveData<ArrayList<Comune>> get() = _listaComuni

    // Province
    private var _listaProvince = MutableLiveData<ArrayList<String>>()
    val listaProvince: LiveData<ArrayList<String>> get() = _listaProvince
    var provinciaSelected = String()

    // Regioni
    private var _listaRegioni = MutableLiveData<ArrayList<String>>()
    val listaRegioni: LiveData<ArrayList<String>> get() = _listaRegioni
    var regioneSelected = String()


    init {
        viewModelScope.launch {
            refreshComuni()
            loadComuni()
            Log.d("TAg", "$listaComuniFromDB")
            }

    }

    /** COMUNI */

    private suspend fun refreshComuni() {
        viewModelScope.launch {
            try {
                // Refresh
                mainRepository.refreshComuniInDB()
            } catch (e: Exception) {
            }
        }
    }

    private suspend fun loadComuni() {
        // Load (from refreshed DB)
        listaComuniFromDB = mainRepository.getListaComuni()
        _listaComuni.value = ArrayList(listaComuniFromDB)
    }

    fun getComuneFromDB(comune: String): Comune {
        lateinit var comuneFromBD: Comune
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
        for (element in listaComuniFromDB) {
            if ((element.nome).lowercase().contains(searchString.lowercase())) {
                searchList.add(element)
            }
        }
        _listaComuni.value = ArrayList(searchList)
    }

    fun getComuniFromProvincia() {
        viewModelScope.launch {
                val listaComuniFromDB = mainRepository.getComuniFromProvincia(provinciaSelected)
                _listaComuni.value = ArrayList(listaComuniFromDB)
        }
    }

    fun getComuniFromRegione() {
        viewModelScope.launch {
                val listaComuniFromDB = mainRepository.getComuniFromRegione(regioneSelected)
                _listaComuni.value = ArrayList(listaComuniFromDB)
        }
    }

    /** PROVINCE */
    fun getProvince() {
        viewModelScope.launch {
                val listaProvinceFromDB = mainRepository.getProvince()
                _listaProvince.value = ArrayList(listaProvinceFromDB)
        }
    }

    fun getProvinceFromRegione() {
        viewModelScope.launch {
                val listaProvinceFromDB = mainRepository.getProvinceFromRegione(regioneSelected)
                _listaProvince.value = ArrayList(listaProvinceFromDB)
        }
    }


    /** REGIONI */
    fun getRegioni() {
        viewModelScope.launch {
            val listaRegioniFromBD = mainRepository.getRegioni()
            _listaRegioni.value = ArrayList(listaRegioniFromBD)
        }
    }

}

