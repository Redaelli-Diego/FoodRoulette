package com.example.foodroulette

import com.example.foodroulette.model.TipoCucina

fun EstrazioneRisto(ristoranti: List<Ristorante>,filtri : List<TipoCucina>) : String? {
    val disponibili = ristoranti.filter { it.tipo !in filtri }
    return disponibili.randomOrNull()?.nome ?: "Nessun ristorante disponibile"
}