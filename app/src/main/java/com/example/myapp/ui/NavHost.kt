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
import com.example.myapp.data.productes


@Composable
fun NavHost1(activity: AppCompatActivity) {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()
    val productes = productes
    // Estableix els productes al ViewModel
    viewModel.setProductes(productes)
    NavHost(navController = navController, startDestination = "Login") {
        composable("App") { AppScreen1(viewModel, navController) }
        composable("Carro") { CarritoScreen1(viewModel, navController) }
        composable("Options") { OptionsScreen1(viewModel, navController) }
        composable("Login") { LoginScreen1(viewModel, navController, activity) }
        composable("Compra") { CompraScreen1(viewModel, navController)}
    }
}

@Composable
fun AppScreen1(viewModel: AppViewModel, navController: NavController) {
    AppScreen(viewModel, navController)
}

@Composable
fun CarritoScreen1(viewModel: AppViewModel, navController: NavController) {
    CarritoScreen(viewModel, navController)
}

@Composable
fun OptionsScreen1(viewModel: AppViewModel, navController: NavController) {
    OptionsScreen(viewModel, navController)
}

@Composable
fun LoginScreen1(viewModel: AppViewModel, navController: NavController, activity: AppCompatActivity) {
    LoginScreen(viewModel, navController, activity)
}

@Composable
fun CompraScreen1(viewModel: AppViewModel = viewModel(), navController: NavController) {
    CompraScreen(viewModel, navController)
}
