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
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.myapp.MainActivity

@Composable
fun LoginScreen(viewModel: AppViewModel, navController: NavController, activity: AppCompatActivity) {
    // Variables per emmagatzemar el text dels inputs
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Disseny principal
    MaterialTheme {
        Surface(
            modifier = Modifier.padding(16.dp).fillMaxSize()
        ) {
            Column {
                Button(
                    onClick = {
                        activity.finishAffinity()
                    },
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Text("Tancar Aplicació")
                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRmNILEZppKJCs1LHgBaUGbbFzQJsv6b5bt-w&s")
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier.size(200.dp).align(Alignment.CenterHorizontally)
                )
                Text(text = "Inici", textAlign = TextAlign.Center, modifier = Modifier.align(Alignment.CenterHorizontally))

                Card(modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)) {
                    Text(
                        text = "Usuari",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    // Caixa d'entrada per a canviar el nom
                    TextField(
                        value = userName,
                        onValueChange = { userName = it },
                        label = { Text("Intrudueix Usuari") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 8.dp)
                    )

                    Text(
                        text = "Intrudueix Contrasenya",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    // Caixa d'entrada per a la contrasenya
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Cambiar Contrasenya") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 8.dp),
                        visualTransformation = PasswordVisualTransformation() // Amaga el text de la contrasenya
                    )
                }

                // Botó per Iniciar Sessió
                Button(
                    onClick = {
                        // Lògica per guardar els inputs
                        val correcte: Boolean = userSession(userName, password, viewModel)
                        if(correcte) {
                            navController.navigate("App")
                        } else{
                            errorMessage = "Error: Usuario o Contraseña incorrectos."
                        }
                    },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Text("Iniciar Sessió")
                }
                // Mostrar mensaje de error si errorMessage no está vacío
                if (errorMessage.isNotEmpty()) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }
        }
    }
}

// Funció per iniciar Sessió
private fun userSession(userName: String, password: String, viewModel: AppViewModel): Boolean {

    val users = usuaris
    var correcte = false

    // Busca un usuario que coincida
    val userFound = users.find { it.nom == userName && it.contrasenya == password }
    if (userFound != null) {
        // Si se encuentra un usuario, asigna correcte a true
        correcte = true
        // Crear una instancia de usuario o actualizar la sesión según sea necesario
        val loggedInUser = User(
            id = userFound.id,
            nom = userFound.nom,
            correu = userFound.correu,
            telefon = userFound.telefon,
            contrasenya = userFound.contrasenya
        )
        viewModel.setUser(loggedInUser)
        println("OptionsScreen Usuario encontrado: ${loggedInUser.nom}")
    } else {
        println("OptionsScreen Usuario no encontrado: Nom: $userName, Contrasenya: $password")
    }

    return correcte
}
