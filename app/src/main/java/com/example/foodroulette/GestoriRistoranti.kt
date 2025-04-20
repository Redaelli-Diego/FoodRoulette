package com.example.foodroulette

import com.example.foodroulette.model.TipoCucina

class GestoreRistoranti(private val ristoranti: MutableList<Ristorante>) {
    fun scegliRistorante(filtri: List<TipoCucina>? = null): Ristorante? {
        val ristorantiDisponibili =
            if (filtri.isNullOrEmpty()) {           //Controllo se la lista dei filtri e vuota e invio la lista intera dei ristoranti se lo e'
                ristoranti
            } else {
                ristoranti.filter { it.tipo !in filtri }                        //Altrimenti filtro la lista
            }

        return if (ristorantiDisponibili.isNotEmpty()) {
            ristorantiDisponibili.random()
        } else null
    }
}
