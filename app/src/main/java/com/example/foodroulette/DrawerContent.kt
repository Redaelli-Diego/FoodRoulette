package com.example.foodroulette

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color

@Composable
fun DrawerContent(onCloseDrawer: () -> Unit) {
    Surface(
        color = Color(0xFFE5D0C1)
    ){
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                "Menu",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(vertical = 20.dp),

                color = Color.Black
            )

            // Esempio di voci di menu
            Button(
                onClick = {
                    // Azione per la voce 1
                    onCloseDrawer()
                },
                modifier = Modifier.width(200.dp)
            ) {
                Text("Home")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    // Azione per la voce 2
                    onCloseDrawer()
                },
                modifier = Modifier.width(200.dp)
            ) {
                Text("Kaboom")
            }
        }
    }

}