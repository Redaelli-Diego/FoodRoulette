package com.example.foodroulette

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.*
import com.example.foodroulette.model.TipoCucina

@Composable
fun FilterDialog(
    ristoranti : List<Ristorante>,
    onDismiss: () -> Unit,
    onResult: (String?) -> Unit
) {

    val tipiCucina = TipoCucina.values().sortedBy{it.label}                                         //Lista di tutti i ristoranti in ENUM tipoCuncina.kt
    var esclusi by remember { mutableStateOf(setOf<TipoCucina>()) }                                 //Lista dei ristoranti che volgio escludere

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Quali Tipi di Cunina vuoi escludere?") },
        text = {
            Column {
                tipiCucina.forEach { tipo ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = esclusi.contains(tipo),
                                onClick = {
                                    esclusi = if (esclusi.contains(tipo)) esclusi - tipo else esclusi + tipo
                                }
                            )
                    ) {
                        Checkbox(
                            checked = esclusi.contains(tipo),
                            onCheckedChange = { isChecked ->
                                esclusi = if (isChecked) esclusi + tipo else esclusi - tipo
                            }
                        )
                        Text(tipo.label)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onResult(EstrazioneRisto(ristoranti,esclusi.toList()))                                                   //Chiamata alla funzione di sorteggio inserita qui passando gli elementi della lista finale
                onDismiss()
            }) {
                Text("Applica")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Annulla")
            }
        }
    )
}