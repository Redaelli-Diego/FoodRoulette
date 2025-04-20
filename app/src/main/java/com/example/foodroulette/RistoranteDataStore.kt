package com.example.foodroulette.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodroulette.Ristorante
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Estensione per creare il DataStore
val Context.dataStore by preferencesDataStore(name = "ristoranti_prefs")

object RistoranteDataStore {
    private val KEY_RISTORANTI = stringPreferencesKey("ristoranti")

    // Funzione per salvare la lista di ristoranti
    suspend fun saveRistoranti(context: Context, lista: List<Ristorante>) {
        val json = Gson().toJson(lista) // Convertiamo la lista in JSON
        context.dataStore.edit { preferences ->
            preferences[KEY_RISTORANTI] = json
        }
    }

    // Funzione per caricare la lista di ristoranti
    fun loadRistoranti(context: Context): Flow<List<Ristorante>> {
        return context.dataStore.data.map { preferences ->
            val json = preferences[KEY_RISTORANTI] ?: "[]" // Se non esiste, restituiamo una lista vuota
            val type = object : TypeToken<List<Ristorante>>() {}.type
            Gson().fromJson(json, type) ?: emptyList()
        }
    }
}
