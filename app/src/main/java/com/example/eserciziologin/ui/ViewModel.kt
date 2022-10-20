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

    var listaComuniRV = listOf<Comune>()
    var nomeComuniRV = mutableListOf<String>()

    var nomeComuneSelected =  String()
    private var comuneSelected = Comune(0,
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        0)

    private var _listaComuni = MutableLiveData<ArrayList<Comune>>()
    val listaComuni: LiveData<ArrayList<Comune>> get() = _listaComuni

    init {
        viewModelScope.launch {
            refreshComuni()

        }
    }



    /** PER I COMUNI */

    private suspend fun refreshComuni() {
        viewModelScope.launch {
            try {
                mainRepository.refreshComuniDB()
                listaComuniRV = mainRepository.getListaComuni()
                _listaComuni.value = ArrayList(listaComuniRV)


            } catch (e:Exception) {

            }
        }
    }

      fun getComuneFromDB(comune:String) {
        viewModelScope.launch {
            try {
                comuneSelected = mainRepository.getComune(comune)
                _listaComuni.value = arrayListOf(comuneSelected)
            } catch (e: Exception) {

            }
        }

    }
}