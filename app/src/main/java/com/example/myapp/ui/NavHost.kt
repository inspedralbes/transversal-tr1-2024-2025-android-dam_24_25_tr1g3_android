package com.example.myapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun NavHost1() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Carrito") {
        composable("App") { AppScreen1(navController) }
        composable("Carrito") { CarritoScreen1(navController) }
    }
}

@Composable
fun AppScreen1(navController: NavController) {
    // Aquí és on configurarem el ViewModel com abans
    val viewModel: AppViewModel = viewModel()


    // Botó per navegar a la pantalla de detalls
    Column {
        Button(modifier = Modifier.align(Alignment.End), onClick = { navController.navigate("Carrito") }) {
            Text("Cambiar Pagina")
        }
        AppScreen(viewModel)
    }
}

@Composable
fun CarritoScreen1(navController: NavController) {
    val viewModel: AppViewModel = viewModel()

    // Contingut de la pantalla de detalls
    Column {
        Text(text = "Aquesta és el Carro", fontSize = 24.sp)
        Button(onClick = { navController.navigate("App") }) {
            Text("Cambiar Pagina")
        }
        CarritoScreen(viewModel)
    }
}
