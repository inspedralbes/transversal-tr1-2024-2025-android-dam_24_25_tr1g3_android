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

@Composable
fun OptionsScreen(viewModel: AppViewModel = viewModel()) {
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
                DropdownMenuExample()
                Text(text = "Nom App", textAlign = TextAlign.Center)
                // Mostrar la llista de productes
                ProductList(productes = productes, viewModel = viewModel)
                ProductList(productes = productesSeleccionats, viewModel = viewModel )
            }
        }
    }
}