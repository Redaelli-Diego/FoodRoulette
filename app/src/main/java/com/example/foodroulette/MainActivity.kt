package com.example.foodroulette

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foodroulette.data.RistoranteDataStore
import com.example.foodroulette.ui.theme.FoodRouletteTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: GestioneRistorantiViewModel = viewModel(
                factory = GestioneRistorantiViewModelFactory(LocalContext.current)
            )
            FoodRouletteTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background 
                ) {
                    FoodRoulette(viewModel)
                }
            }
        }
    }
}


@Composable
fun FoodRoulette(viewModel: GestioneRistorantiViewModel) {
    var drawerState = remember {DrawerState(DrawerValue.Closed)}
    var scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Contenuto del menu laterale
            DrawerContent(
                onCloseDrawer = { scope.launch { drawerState.close() } }
            )
        },
        gesturesEnabled = true, // Permetti lo swipe per aprire/chiudere
    ) {
        // Contenuto principale della tua app
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            FoodRouletteApp(
                viewModel = viewModel,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        }
    }

}



@Composable
fun FoodRouletteApp(viewModel: GestioneRistorantiViewModel, modifier: Modifier = Modifier) {
    var showADDialog by remember { mutableStateOf(false) } //Variabile per form di aggiunta ristoranti
    var deleteDialog by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }
    var ristoranti = viewModel.ristoranti
    val context = LocalContext.current
    var ristoranteSelezionato by remember { mutableStateOf<String?>(null) }
    var mostraLista by remember { mutableStateOf(false) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {                                                                                                  //Necessaria per riprendere i ristoranti dopo aver chiuso la app
        RistoranteDataStore.loadRistoranti(context).collect { lista ->
            if (lista.isNotEmpty()) {
                ristoranti = lista.toMutableList() // Aggiorniamo la lista con i dati salvati
            }
        }
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                onCloseDrawer = { scope.launch { drawerState.close() } }
            )
        },
        content =
        {// -------------------Aggiunta pulsante del menu----------------------------------------
            Surface(color = Color(0xFFB67233)) {
                Button(
                    onClick = { scope.launch { drawerState.open() } },
                    modifier = Modifier
                        .padding(
                            vertical = 50.dp
                        )
                        .size(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5DC)),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),

                    ) {
                    Text(stringResource(R.string.Menu));
                }
// -------------------Stampa del ristorante selezionato-----------------------------------------
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier.height(200.dp)
                    ) {
//                        ristoranteSelezionato?.let { ristorante ->
//                            Text("${ristorante}", fontSize = 40.sp, fontWeight = FontWeight.Bold)
//                        }
                        SlotMachineRistorante(
                            ristoranti = viewModel.ristoranti.map { it.nome }, // Assumendo che sia una lista di oggetti Ristorante
                            ristoranteEstratto = ristoranteSelezionato,
                            modifier = Modifier.height(200.dp)
                        )
                    }
// -------------------Stampa della lista ristoranti-----------------------------------------
                    Button(
                        onClick = { mostraLista = true },
                        modifier = Modifier.size(200.dp, 60.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5DC)),
                    ) {
                        Text("Visualizza Lista")
                    }
                    Spacer(modifier = Modifier.height(20.dp))
// -------------------SPIN HERE-----------------------------------------
                    Button(
                        onClick = { showFilterDialog = true },
                        modifier = Modifier.size(200.dp, 60.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5DC)),
                    ) {
                        Text(
                            text = "ESTRAZIONE",
                            //size = 200.dp,
                            modifier = Modifier
                                .fillMaxWidth(),
                            //.size()
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
// -------------------Add and delete here-----------------------------------------
                    Row {
                        Button(
                            onClick = { showADDialog = true },
                            modifier = Modifier.size(110.dp, 60.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5DC)),
                        ) {
                            Text(stringResource(R.string.add_option))
                        }
                        Button(
                            onClick = { deleteDialog = true },
                            modifier = Modifier.size(110.dp, 60.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF5F5DC)),
                        ) {
                            Text(stringResource(R.string.mod_option))
                        }
                    }
                }
            }
// -------------------Aggiunta Ristorante-----------------------------------------
            if (showADDialog) {
                ristoadd(                                                                                   //Dove viene aggiunto il nuovo ristorante alla lista
                    viewModel = viewModel,
                    onDismiss = { showADDialog = false }
                )
            }
// -------------------Rimozione Ristorante-----------------------------------------
            if (deleteDialog) {
                ristoDel(
                    viewModel = viewModel,
                    onDismiss = { deleteDialog = false },

                    )
            }
// -------------------Gestione tasto Estrazione Con Filtri-----------------------------------------
            if (showFilterDialog) {
                FilterDialog(
                    ristoranti = ristoranti,
                    onDismiss = { showFilterDialog = false },
                    onResult = { risultato -> ristoranteSelezionato = risultato }

                )
            }
// -------------------Gestione tasto Mostra Lista-----------------------------------------
            if (mostraLista) {
                ListaRistorantiDialog(
                    viewModel = viewModel,
                    onDismiss = { mostraLista = false }
                )
            }

        }
    )


}

@Composable
fun RisultatoAnimato(ristoranteEstratto: String?) {
    val scale by animateFloatAsState(
        targetValue = if (ristoranteEstratto != null) 2f else 2f,
        animationSpec = spring(dampingRatio = 0.1f, stiffness = 10f)                                    //Gestisce il rimbalzo e la durata

    )

    Text(
        text = ristoranteEstratto ?: "Premi Estrazione",
        fontSize = 30.sp,
        modifier = Modifier.scale(scale),
        color = Color.Black
    )
}



