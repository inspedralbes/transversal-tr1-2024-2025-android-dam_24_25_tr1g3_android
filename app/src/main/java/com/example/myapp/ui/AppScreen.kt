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
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppScreen(viewModel: AppViewModel = viewModel(), navController: NavController) {
    // Llista de productes
    val productes by viewModel.products.collectAsState()

    // Obteniu la llista de productes seleccionats
    val productesSeleccionats by viewModel.productesSeleccionats.collectAsState()

    // Disseny principal
    MaterialTheme {
        Surface(
            modifier = Modifier.padding(16.dp).fillMaxSize()
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DropdownMenuExample(viewModel, navController)
                    Button(onClick = { navController.navigate("Carro") }) {
                        Text("Carro")
                    }
                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRmNILEZppKJCs1LHgBaUGbbFzQJsv6b5bt-w&s")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally)
                )
                Button(modifier = Modifier.align(Alignment.CenterHorizontally),onClick = { navController.navigate("Compra") }) {
                    Text("Comprar Productes Seleccionats")
                }
                Text(text = "Productes", textAlign = TextAlign.Center, fontSize = 30.sp)
                // Mostrar la llista de productes
                ProductList(productes = productes, viewModel = viewModel)
                ProductList(productes = productesSeleccionats, viewModel = viewModel )
            }
        }
    }
}

@Composable
fun DropdownMenuExample(viewModel: AppViewModel, navController: NavController) {
    // Estat per controlar la visibilitat del menú
    var expanded by remember { mutableStateOf(false) }

    // Contingut del botó i el menú desplegable
    Box(
        modifier = Modifier
            .padding(0.dp)
    ) {
        // Botó que activa o desactiva el menú desplegable
        Button(modifier = Modifier.align(Alignment.TopStart),
            onClick = { expanded = true },
            colors = ButtonColors(
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.Gray,
                contentColor = Color.Black,
                containerColor = Color.LightGray
            )
        ) {
            Text("···")
        }

        // Menú desplegable
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(focusable = true),
            offset = androidx.compose.ui.unit.DpOffset(x = 0.dp, y = 0.dp) // Posició del menú

        ) {
            Column(modifier = Modifier.fillMaxSize().align(Alignment.CenterHorizontally)) {
                // Elements del menú
                DropdownMenuItem(
                    text = { Text("Opcions de Perfil") },
                    onClick = {
                        expanded = false
                        navController.navigate("Options")
                        // Acció per a l'opció 1
                    }
                )
                DropdownMenuItem(
                    text = { Text("Tancar Sessió") },
                    onClick = {
                        expanded = false
                        viewModel.setUser(emptyUser())
                        navController.navigate("Login")
                        // Acció per a l'opció 2
                    }
                )
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
                    ProductItem(producte = producte, productes = productes, viewModel = viewModel, modifier = Modifier.weight(5f))
                }
            }
        }
    }
}

@Composable
fun ProductItem(producte: Product, productes: List<Product>,viewModel: AppViewModel, modifier: Modifier) {

    var isSelected by remember { mutableStateOf(producte.select) }
    var cantidad by remember { mutableStateOf(producte.cantitatCompra.toString()) }
    // Representa un producte dins d'una targeta
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
            .clickable {
                isSelected = !isSelected
                producte.select = isSelected
                producte.cantitatCompra = cantidad.toInt()
                viewModel.setProductes(productes) // Actualitza l'array al ViewModel
            }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                Text(text = "${producte.nom} ${producte.preu}€\nRestants: ${producte.stock}\nCantitat a Comprar:")

                // Agregar TextField para campo numérico
                OutlinedTextField(
                    value = cantidad,
                    onValueChange = { newValue ->
                        // Permitir solo números
                        if (newValue.all { it.isDigit() }) {
                            cantidad = newValue
                        }
                    },
                    label = { Text("Cantidad") },
                    modifier = Modifier.fillMaxWidth(),

                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(producte.linkimatge)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize().padding(vertical = 5.dp)
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
    AppScreen(navController = rememberNavController())
}
