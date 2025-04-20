package com.example.foodroulette.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.foodroulette.model.TipoCucina

@Composable
fun SelezioneTipoCucina(
    tipoSelezionato: TipoCucina,
    onTipoCucinaChange: (TipoCucina) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        // Bottone che mostra il tipo selezionato e apre il menu
        OutlinedButton(onClick = { expanded = true }) {
            Text(text = "Tipo: ${tipoSelezionato.label}")
        }

        // Il menu a discesa
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            TipoCucina.values().forEach { tipo ->
                DropdownMenuItem(
                    text = { Text(tipo.label) },
                    onClick = {
                        onTipoCucinaChange(tipo)
                        expanded = false
                    }
                )
            }
        }
    }
}