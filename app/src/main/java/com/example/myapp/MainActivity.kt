package com.example.myapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.myapp.ui.*
import com.example.myapp.ui.theme.AppTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppTheme {
                Surface(modifier = Modifier.padding(16.dp).fillMaxSize()){
                    NavHost(navController = navController, startDestination = "Carrito") {
                        composable("App") { AppScreen1(navController) }
                        composable("Carrito") { CarritoScreen1(navController) }
                    }
                }
            }
        }
    }
}

@Composable
fun AppScreen1(navController: NavController) {
    // Aquí és on configurarem el ViewModel com abans
    val viewModel: AppViewModel = viewModel()
    AppScreen(viewModel)

    // Botó per navegar a la pantalla de detalls
    Column {
        Button(onClick = { navController.navigate("Carrito") }) {
            Text("Cambiar Pagina")
        }
    }
}

@Composable
fun CarritoScreen1(navController: NavController) {
    val viewModel: AppViewModel = viewModel()
    CarritoScreen(viewModel)
    // Contingut de la pantalla de detalls
    Column {
        Text(text = "Aquesta és la pantalla de detalls", fontSize = 24.sp)
        Button(onClick = { navController.navigate("App") }) {
            Text("Cambiar Pagina")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "Carrito") {
        composable("App") { AppScreen1(navController) }
        composable("Home") { CarritoScreen1(navController) }
    }
}