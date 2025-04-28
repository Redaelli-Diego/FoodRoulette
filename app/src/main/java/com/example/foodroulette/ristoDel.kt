package com.example.foodroulette

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight


@Composable
fun ristoDel(
    viewModel: GestioneRistorantiViewModel,
    onDismiss: () -> Unit,
) {
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

            Button(onClick = {
                val ristodel = viewModel.ristoranti.find{it.nome == nome}
                if(ristodel!= null) {
                    viewModel.eliminaRistorante(ristodel.nome)
                    onDismiss()
                }
                },

                colors = ButtonDefaults.buttonColors(containerColor  =  Color(0xFFF5F5DC))) {
                Text("Elimina")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor  =  Color(0xFFF5F5DC))) {
                Text("Annulla")
            }
        }
    )
}