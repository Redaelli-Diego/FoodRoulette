package com.example.foodroulette

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodroulette.data.RistoranteDataStore
import kotlinx.coroutines.launch

class GestioneRistorantiViewModel(
    private val context: Context
) : ViewModel() {

    private val _ristoranti = mutableStateListOf<Ristorante>()
    val ristoranti: List<Ristorante> get() = _ristoranti

    init {
        loadRistoranti()
    }

    private fun loadRistoranti() {
        viewModelScope.launch {
            RistoranteDataStore.loadRistoranti(context).collect { lista ->
                _ristoranti.clear()
                _ristoranti.addAll(lista)
            }
        }
    }

    private suspend fun saveRistoranti() {
        RistoranteDataStore.saveRistoranti(context, _ristoranti)
    }

    fun aggiungiRistorante(ristorante: Ristorante) {
        _ristoranti.add(ristorante)
        viewModelScope.launch {
            saveRistoranti()
        }
    }

    fun eliminaRistorante(nome: String) {
        _ristoranti.removeAll { it.nome == nome }
        viewModelScope.launch {
            saveRistoranti()
        }
    }
}

