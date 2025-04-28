package com.example.foodroulette

import com.example.foodroulette.model.TipoCucina

data class Ristorante(

    val nome : String,
    val costo: Int,
    val isFastFood : Boolean,
    val tipo : TipoCucina,
)