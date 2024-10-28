package com.example.myapp.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.simulateHotReload
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapp.data.*
import androidx.compose.ui.Alignment
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost

@Composable
fun AppScreen(viewModel: AppViewModel = viewModel()) {
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
                // Mostrar la llista de productes
                ProductList(productes = productes, viewModel = viewModel)
                ProductList(productes = productesSeleccionats, viewModel = viewModel )
            }
        }
    }
}

@Composable
fun ProductList(productes: List<Product>, viewModel: AppViewModel) {
    // Utilitzem un LazyColumn per crear una llista vertical
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        // Iterem per cada dos elements de la llista
        items(productes.chunked(2)) { rowProducts ->
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxSize()
            ) {
                // Per cada element de la fila (2 per fila)
                for (producte in rowProducts) {
                    ProductItem(producte = producte, productes = productes,viewModel = viewModel,modifier = Modifier.weight(5f))
                }
            }
        }
    }
}

@Composable
fun ProductItem(producte: Product, productes: List<Product>,viewModel: AppViewModel, modifier: Modifier) {

    var isSelected by remember { mutableStateOf(producte.select) }
    // Representa un producte dins d'una targeta
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .clickable {
                isSelected = !isSelected
                producte.select = isSelected
                viewModel.setProductes(productes) // Actualitza l'array al ViewModel
            }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(text = "${producte.nom} ${producte.preu}€")
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(producte.linkimatge)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(if (producte.select) Color.Green else Color.Red)
                    .align(Alignment.TopEnd) // Alinea el cuadrado en la esquina superior derecha
                    .padding(4.dp) // Ajusta el espacio alrededor si es necesario
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppScreen()
}
