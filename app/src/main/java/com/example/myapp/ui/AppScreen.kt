package com.example.myapp.ui
/*
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapp.R
import com.example.myapp.ui.theme.AppTheme


@Composable
fun AppScreen(appViewModel: AppViewModel = viewModel()){
    val gameUiState by appViewModel.uiState.collectAsState()
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Row(
        modifier = Modifier
        .statusBarsPadding()
        .verticalScroll(rememberScrollState())
        .safeDrawingPadding()
        .padding(mediumPadding),
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .safeDrawingPadding()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.app_name),
                style = typography.titleLarge,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    AppTheme {
        AppScreen()
    }
} */

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapp.data.*

@Composable
fun AppScreen() {
    // Llista de productes
    val productes = listOf(
        Product(nom = "Producte 1", linkimatge = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRmNILEZppKJCs1LHgBaUGbbFzQJsv6b5bt-w&s", preu = 10),
        Product(nom = "Producte 2", linkimatge = "https://img.freepik.com/vector-premium/icono-perfil-humano-ilustracion-vectorial-genero_276184-158.jpg", preu = 20),
        Product(nom = "Producte 3", linkimatge = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQFgfz5qkNbQdluKLOIPKAfX-SMdelQ-5u2ng&s", preu = 30),
        Product(nom = "Producte 4", linkimatge = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcScxjghTQcnJXrIE4SaETIbuDX7DOFqMHe9wQ&s", preu = 40),
        Product(nom = "Producte 5", linkimatge = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJa2gCDBRx9aDbGC4lfk_vX0Cb96VvGCC7Pw&s", preu = 50),
        Product(nom = "Producte 6", linkimatge = "https://media.istockphoto.com/id/1147544807/es/vector/no-imagen-en-miniatura-gr%C3%A1fico-vectorial.jpg?s=612x612&w=0&k=20&c=Bb7KlSXJXh3oSDlyFjIaCiB9llfXsgS7mHFZs6qUgVk=", preu = 60)
    )

    // Disseny principal
    MaterialTheme {
        Surface(
            modifier = Modifier.padding(16.dp).fillMaxSize()
        ) {
            // Mostrar la llista de productes
            ProductList(productes = productes)
        }
    }
}

@Composable
fun ProductList(productes: List<Product>) {
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
                    ProductItem(productName = producte.nom, link = producte.linkimatge, preu = producte.preu, modifier = Modifier.weight(5f))
                }
            }
        }
    }
}

@Composable
fun ProductItem(productName: String, link: String, preu: Int, modifier: Modifier) {
    // Representa un producte dins d'una targeta
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = "$productName $preu€")
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(link)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppScreen()
}
