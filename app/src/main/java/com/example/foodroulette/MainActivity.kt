package com.example.foodroulette

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.foodroulette.data.RistoranteDataStore
import com.example.foodroulette.ui.theme.FoodRouletteTheme
import kotlinx.coroutines.launch



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodRouletteTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background 
                ) {
                    FoodRoulette()
                }
            }
        }
    }
}

@Preview
@Composable
fun FoodRoulette() {
    FoodRouletteApp(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}

@Composable
fun FoodRouletteApp(modifier: Modifier = Modifier) {
    var showADDialog by remember { mutableStateOf(false) } //Variabile per form di aggiunta ristoranti
    var deleteDialog by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }
    var ristoranti by remember {mutableStateOf<List<Ristorante>>(emptyList())}
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var ristoranteSelezionato by remember { mutableStateOf<Ristorante?>(null) }
    var filtriAttivi by remember { mutableStateOf(emptyList<String>()) }
    var mostraLista by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {                                                                                                  //Necessaria per riprendere i ristoranti dopo aver chiuso la app
        RistoranteDataStore.loadRistoranti(context).collect { lista ->
            if (lista.isNotEmpty()) {
                ristoranti = lista // Aggiorniamo la lista con i dati salvati
            }
        }
    }

    Surface (color = Color(0xFFB67233)) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier.height(200.dp)
            ) {
                ristoranteSelezionato?.let { ristorante ->
                    Text( "${ristorante.nome}", fontSize = 50.sp, fontWeight = FontWeight.Bold)
                }
            }
// -------------------Stampa della lista ristoranti-----------------------------------------
            Button(
                onClick = { mostraLista = true },
                modifier = Modifier.size(200.dp, 60.dp),
                colors = ButtonDefaults.buttonColors(containerColor  =  Color(0xFFF5F5DC)),
            ) {
                Log.d("DEBUG","Ristoranti: ${ristoranti.size} elementi, nel bottone")
                Text("Visualizza Lista")
            }
            Spacer(modifier = Modifier.height(20.dp))
// -------------------SPIN HERE-----------------------------------------
            Button(
                onClick = {showFilterDialog = true},
                modifier = Modifier.size(200.dp, 60.dp),
                colors = ButtonDefaults.buttonColors(containerColor  =  Color(0xFFF5F5DC)),
            ) {
                Text(stringResource(R.string.ADV_Spin))
            }
            Spacer(modifier = Modifier.height(20.dp))
// -------------------Add and delete here-----------------------------------------
            Row {
                Button(
                    onClick = { showADDialog = true },
                    modifier = Modifier.size(110.dp, 60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor  =  Color(0xFFF5F5DC)),
                ) {
                    Text(stringResource(R.string.add_option))
                }
                Button(
                    onClick = { deleteDialog = true },
                    modifier = Modifier.size(110.dp, 60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor  =  Color(0xFFF5F5DC)),
                ) {
                    Text(stringResource(R.string.mod_option))
                }
            }
// -------------------Stop Grafica-----------------------------------------
        }
    }
    if (showADDialog) {
        ristoadd(                                                                                           //Dove viene aggiunto il nuovo ristorante alla lista
            onDismiss = { showADDialog = false },
            onSave = { nuovoRistorante ->
               ristoranti = Ristorante.aggiungiRistorante(ristoranti,nuovoRistorante)
                coroutineScope.launch {
                    RistoranteDataStore.saveRistoranti(context, ristoranti) // Salviamo nel DataStore
                }
                showADDialog = false // Chiude il form dopo il salvataggio
            }
        )
    }
    if (deleteDialog) {
        ristoDel(
            onDismiss = { deleteDialog = false },
            onDelete = { nomeRistorante ->
                ristoranti = ristoranti.filterNot { it.nome.equals(nomeRistorante, ignoreCase = true) }
                coroutineScope.launch {
                    RistoranteDataStore.saveRistoranti(context, ristoranti) // Salviamo nel DataStore
                }
                deleteDialog = false
            }
        )
    }

    if (showFilterDialog) {
        FilterDialog(
            onDismiss = { showFilterDialog = false },
            onApplyFilters = { filtriAttivi = it }
        )
    }

    if (mostraLista) {
        Log.d("DEBUG","Ristoranti: ${ristoranti.size} elementi, nella lista")
        ListaRistorantiDialog(
            ristoranti = ristoranti,
            onDismiss = { mostraLista = false }
        )
    }

}


