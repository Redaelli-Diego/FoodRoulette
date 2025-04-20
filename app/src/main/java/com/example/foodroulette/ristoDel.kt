package com.example.foodroulette

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight


@Composable
fun ristoDel(onDismiss: () -> Unit, onDelete: (String) -> Unit) {
    var nome by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Elimina un ristorante") },
        text = {
            Column {
                Text("Inserisci il nome del ristorante da eliminare:")
                TextField(
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome ristorante") }
                )
            }
        },
        confirmButton = {
            Button(onClick = { onDelete(nome) }) {
                Text("Elimina")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Annulla")
            }
        }
    )
}

@Composable
fun RistoranteItem(ristorante: Ristorante) {
    Row(
        //modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
           // modifier = modifier,
        ) {
            Text("Nome: ${ristorante.nome}", fontWeight = FontWeight.Bold)
            Text("Cucina: ${ristorante.tipo}")
            Text("Valutazione: ${ristorante.costo}/5")
            Text("Tipo: ${if (ristorante.isFastFood) "Fast Food" else "Ristorante"}")
        }
    }
}