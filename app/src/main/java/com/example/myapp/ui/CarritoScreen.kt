package com.example.myapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapp.data.productes

@Composable
fun CarritoScreen(viewModel: AppViewModel = viewModel(), navController: NavController) {
    // Llista de productes
    val productes = productes

    // Estableix els productes al ViewModel
    viewModel.setProductes(productes)

    // Obteniu la llista de productes seleccionats
    val productesSeleccionats by viewModel.productesSeleccionats.collectAsState()

    // Disseny principal
    MaterialTheme {
        Surface(
            modifier = Modifier.padding(16.dp).fillMaxSize()
        ) {
            Column {
                Text(text = "Nom App", textAlign = TextAlign.Center)
                ProductList(productes = productesSeleccionats, viewModel = viewModel )
            }
        }
    }
}