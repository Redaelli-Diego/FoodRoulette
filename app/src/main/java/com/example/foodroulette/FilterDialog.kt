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

@Composable
fun FilterDialog(
    onDismiss: () -> Unit,
    onApplyFilters: (List<String>) -> Unit
) {
    val tipiCucina = listOf("Italiano", "Cinese", "Messicano", "Giapponese", "Fast Food")
    var esclusi by remember { mutableStateOf(setOf<String>()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Escludi Tipi di Cucina") },
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
                        Text(tipo)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                onApplyFilters(esclusi.toList())
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