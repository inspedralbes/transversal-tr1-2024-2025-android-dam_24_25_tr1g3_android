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
import androidx.compose.foundation.Image
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun OptionsScreen(navController: NavController) {
    // Variables per emmagatzemar el text dels inputs
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var card by remember { mutableStateOf("") }

    // Disseny principal
    MaterialTheme {
        Surface(
            modifier = Modifier.padding(16.dp).fillMaxSize()
        ) {
            Column {
                Row {
                    Button(onClick = { navController.navigate("App") }) {
                        Text(text = "Inici", textAlign = TextAlign.Center)
                    }
                    // Aquí pots afegir la imatge si cal
                }

                Text(text = "Usuari", textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.CenterHorizontally))
                // Caixa d'entrada per a canviar el nom
                TextField(
                    value = userName,
                    onValueChange = { userName = it },
                    label = { Text("Cambiar Nom") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                Text(text = "Contrasenya", textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.CenterHorizontally))
                // Caixa d'entrada per a la contrasenya
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Cambiar Contrasenya") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    visualTransformation = PasswordVisualTransformation() // Amaga el text de la contrasenya
                )

                Text(text = "Correu", textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.CenterHorizontally))
                // Caixa d'entrada per al correu
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Cambiar Correu") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                Text(text = "Targeta", textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.CenterHorizontally))
                // Caixa d'entrada per a la targeta
                TextField(
                    value = card,
                    onValueChange = { card = it },
                    label = { Text("Cambiar Targeta") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                )

                // Botó per guardar els inputs
                Button(
                    onClick = {
                        // Lògica per guardar els inputs
                        saveUserData(userName, password, email, card)

                        // Buidar els inputs
                        userName = ""
                        password = ""
                        email = ""
                        card = ""
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text("Guardar Canvis")
                }
            }
        }
    }
}

// Funció per guardar les dades de l'usuari
private fun saveUserData(userName: String, password: String, email: String, card: String) {
    //Emmagatzemar en una base de dades
    Log.d("OptionsScreen", "Nom: $userName, Contrasenya: $password, Correu: $email, Targeta: $card")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview1() {
    OptionsScreen(navController = rememberNavController())
}