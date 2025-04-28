package com.example.foodroulette

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GestioneRistorantiViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GestioneRistorantiViewModel::class.java)) {
            return GestioneRistorantiViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}