package com.example.foodroulette

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun ListaRistorantiDialog(
    viewModel: GestioneRistorantiViewModel,
    onDismiss: () -> Unit
) {
    val ristoranti = viewModel.ristoranti
    Dialog(onDismissRequest = onDismiss) {
        Box () {
        Surface(
            modifier = Modifier
                .align(Alignment.TopCenter),
            shape = RoundedCornerShape(10.dp),
            color = Color(0xFFF5F5DC),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),

                ) {
                Text(
                    text = "Lista Ristoranti",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    textAlign = TextAlign.Center,
                    color = Color.Black,

                    )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),  // 2 colonne
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(ristoranti) { ristorante ->
                        Card(
                            modifier = Modifier
                                .fillMaxSize(),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardColors(
                                containerColor = Color(0xFFA4EAF5),
                                contentColor = Color.DarkGray,
                                disabledContainerColor = Color.Transparent,
                                disabledContentColor = Color.Transparent
                            )
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(ristorante.nome, fontWeight = FontWeight.Bold)
                                Text("🍽 ${ristorante.tipo?.label ?: "N/D"}")
                                Text(stringResource(R.string.Money).repeat(ristorante.costo))
                                Text(if (ristorante.isFastFood) "🍔 Fast Food" else "🍽 Ristorante")
                            }
                            Button(
                                onClick = { viewModel.eliminaRistorante(ristorante.nome) },
                                modifier = Modifier
                                    .align(Alignment.End)
                                    .size(30.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFFF52727
                                    )
                                ),
                                shape = CircleShape,
                                contentPadding = PaddingValues(0.dp),
                            ) {
                                Text(
                                    text = stringResource(R.string.Delete),
                                    fontSize = 15.sp,
                                );

                            }
                        }
                    }
                }
            }
        }
            Spacer(modifier = Modifier.size(10.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            color = Color.Transparent,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5DC))
                ) {
                    Text("Chiudi")
                }
            }

        }
        }

    }
}