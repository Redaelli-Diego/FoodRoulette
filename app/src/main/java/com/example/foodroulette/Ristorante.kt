package com.example.foodroulette

import com.example.foodroulette.model.TipoCucina

data class Ristorante(

    val nome : String,
    val costo: Int,
    val isFastFood : Boolean,
    val tipo : TipoCucina,
){
    companion object{
        fun aggiungiRistorante(lista: List<Ristorante>, nuovoRistorante: Ristorante): List <Ristorante> {
            return lista + nuovoRistorante
        }
    }
}