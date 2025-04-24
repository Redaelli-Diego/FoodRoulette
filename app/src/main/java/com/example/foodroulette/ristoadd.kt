package com.example.foodroulette

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.foodroulette.model.TipoCucina
import androidx.compose.material3.*
import androidx.compose.ui.res.stringResource
import com.example.foodroulette.ui.components.SelezioneTipoCucina


@Composable
fun ristoadd(onDismiss: () -> Unit, onSave: (Ristorante) -> Unit) {
    var nome by remember { mutableStateOf("") }
    var tipoCucina by remember { mutableStateOf(TipoCucina.ITALIANA) }
    var costo by remember { mutableStateOf(1) }
    var isFastFood by remember { mutableStateOf(false) }


    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Aggiungi un nuovo ristorante") },
        text = {
            Column {
                TextField(                                              //Text field per inserire il nome del ristorante
                    value = nome,
                    onValueChange = { nome = it },
                    label = { Text("Nome") }
                )
                Spacer(modifier = Modifier.height(20.dp))

                SelezioneTipoCucina(
                    tipoSelezionato = tipoCucina,
                    onTipoCucinaChange = {tipoCucina = it}
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text("Costo medio " + stringResource(R.string.Money))
                Row {
                    listOf(1, 2, 3).forEach { stelle ->                 //Inserimento di valutazione
                        Button(
                            onClick = { costo = stelle },
                            colors = ButtonDefaults.buttonColors(
                                containerColor  = if (costo == stelle) Color(0xFFF5F5DC) else Color.Transparent
                            ),
                            //modifier = Modifier.padding(4.dp)
                        ) {
                            Text(stringResource(R.string.Money).repeat(stelle))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Switch per indicare se è Fast Food
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Fast Food")
                    Spacer(modifier = Modifier.width(20.dp))
                    Switch(
                        checked = isFastFood,
                        onCheckedChange = { isFastFood = it }
                    )
                }
            }
        },
        //------------------------CONFERMA DI INSERIMENTO--------------------------------
        confirmButton = {
            Button(onClick = {

                if (nome.isNotBlank()) {
                    onSave(Ristorante(nome, costo, isFastFood, tipoCucina))
                }
            },colors = ButtonDefaults.buttonColors(containerColor  =  Color(0xFFF5F5DC))
            ) {
                Text("Salva")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss
            ,colors = ButtonDefaults.buttonColors(containerColor  =  Color(0xFFF5F5DC))
            ) {
                Text("Annulla")
            }
        }
    )
}


