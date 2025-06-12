package com.example.foodroulette

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun SlotMachineRistorante(
    ristoranti: List<String>,
    ristoranteEstratto: String?,
    modifier: Modifier = Modifier
) {
    var animazioneInCorso by remember { mutableStateOf(false) }
    var indiceCorrente by remember { mutableStateOf(0) }
    val velocitàAnimazione = 100L // ms tra un cambio e l'altro
    val durataTotale = 2000L // ms durata totale animazione

    // Avvia l'animazione quando viene estratto un nuovo ristorante
    LaunchedEffect(ristoranteEstratto) {
        if (ristoranteEstratto != null) {
            animazioneInCorso = true
            val startTime = System.currentTimeMillis()
            val endTime = startTime + durataTotale

            // Fase 1: Scorrimento veloce
            while (System.currentTimeMillis() < endTime - 500) {
                indiceCorrente = (indiceCorrente + 1) % ristoranti.size
                delay(velocitàAnimazione)
            }

            // Fase 2: Rallentamento progressivo
            var tempoRimanente = endTime - System.currentTimeMillis()
            while (tempoRimanente > 0) {
                indiceCorrente = (indiceCorrente + 1) % ristoranti.size
                delay(maxOf(velocitàAnimazione * 2, tempoRimanente / 10))
                tempoRimanente = endTime - System.currentTimeMillis()
            }

            // Fase 3: Ferma sul risultato corretto
            indiceCorrente = ristoranti.indexOf(ristoranteEstratto)
            animazioneInCorso = false
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(Color(0xFFB67233), RoundedCornerShape(8.dp))
    ) {
        Text(
            text = if (animazioneInCorso) ristoranti[indiceCorrente]
            else ristoranteEstratto ?: "Premi Estrazione",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )
    }
}