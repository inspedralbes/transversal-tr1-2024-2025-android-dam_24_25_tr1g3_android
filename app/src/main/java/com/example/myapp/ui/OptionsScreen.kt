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
fun OptionsScreen(viewModel: AppViewModel, navController: NavController) {
    // Variables per emmagatzemar el text dels inputs
    val user by viewModel.user.collectAsState()
    println(viewModel.user.collectAsState())
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

                Column(modifier = Modifier.padding(16.dp)) {

                    println("Usuario: ${user.nom}, Contraseña: ${user.contrasenya}, Correo: ${user.correu}, Teléfono: ${user.telefon}")

                    Card(modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Usuari",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            // Caixa d'entrada per a canviar el nom
                            Text(
                                text = user.nom,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                            )
                        }
                    }

                    Card(modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Contrasenya",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            // Caixa d'entrada per a la contrasenya
                            Text(
                                text = user.contrasenya,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                            )
                        }
                    }

                    Card(modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Correu",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            // Caixa d'entrada per al correu
                            Text(
                                text = user.correu,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                            )
                        }
                    }

                    Card(modifier = Modifier.padding(vertical = 8.dp).fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Telefon",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                            // Caixa d'entrada per al telefon
                            Text(
                                text = user.telefon.toString(),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                            )
                        }
                    }
                }

                // Botó per guardar els inputs
                /*Button(
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
                    Text("Tancar Sessio")
                }*/
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
    val viewModel: AppViewModel = viewModel()
    OptionsScreen(viewModel, navController = rememberNavController())
}