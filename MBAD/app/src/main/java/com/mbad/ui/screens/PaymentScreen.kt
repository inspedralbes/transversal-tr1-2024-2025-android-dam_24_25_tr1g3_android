package com.mbad.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mbad.R
import com.mbad.ui.StoreViewModel
import com.mbad.ui.theme.MBADTheme


@Composable
fun PaymentScreen(
    modifier: Modifier = Modifier,
    storeViewModel: StoreViewModel = viewModel(),
){
    val storeUiState by storeViewModel.uiState.collectAsState()
    val BuyUiState by storeViewModel.buyUiState.collectAsState()

    var orderResponse = BuyUiState.orderResponse

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.start_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.5F,
            modifier = Modifier.fillMaxSize()
        )
        if(BuyUiState.Success){
            Card(
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {

                Card {
                    Text(
                        text = "Numero de la comanda: ${orderResponse.num_pedido}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = modifier.
                                        padding(horizontal = 16.dp, vertical = 2.dp)
                    )

                    Text(
                        text = "Data: ${orderResponse.fecha}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = modifier.
                                        padding(horizontal = 16.dp, vertical = 2.dp)
                    )
                    val formattedTotalPrice = if (orderResponse.total_pedido % 1 == 0.0) {
                        // Si es un número entero, no mostramos decimales
                        String.format("%.0f", orderResponse.total_pedido)
                    } else {
                        // Si tiene decimales, usamos String.format para mostrar hasta 2 decimales
                        String.format("%.2f", orderResponse.total_pedido).replace('.', ',') // Reemplaza el punto por una coma
                    }

                    Text(
                        text = "Preu total: ${formattedTotalPrice}€",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = modifier.
                        padding(horizontal = 16.dp, vertical = 2.dp)
                    )

                    Text(
                        text = "Productes:",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = modifier.
                                        padding(horizontal = 16.dp, vertical = 2.dp)
                    )

                    LazyColumn(
                        modifier = Modifier
                            .weight(1f) // Permite que el listado de productos sea desplazable
                            .padding(bottom = 16.dp)
                    ) {
                        items(orderResponse.productos.size) { index ->
                            val OrderItem = orderResponse.productos[index]
                            storeUiState.StoreProducts.find { it.id == OrderItem.ID_producto }?.let {
                                Box(
                                    modifier = Modifier.padding(horizontal = 16.dp) // Margen alrededor de la Card
                                ) {
                                    Card(
                                        modifier = Modifier.padding(vertical = 5.dp).fillMaxWidth(),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(0xFFCCCCCC),
                                        ),

                                        ) {
                                        Text(
                                            text = "${it.name}",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = modifier.padding(
                                                horizontal = 16.dp,
                                                vertical = 2.dp
                                            )
                                        )
                                        Text(
                                            text = "Quantitat: ${OrderItem.cantidad}",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = modifier.padding(
                                                horizontal = 16.dp,
                                                vertical = 2.dp
                                            )
                                        )
                                        val formattedPrice = if (OrderItem.precio_unitario % 1 == 0.0) {
                                            // Si es un número entero, no mostramos decimales
                                            String.format("%.0f", OrderItem.precio_unitario)
                                        } else {
                                            // Si tiene decimales, usamos String.format para mostrar hasta 2 decimales
                                            String.format("%.2f", OrderItem.precio_unitario).replace('.', ',') // Reemplaza el punto por una coma
                                        }
                                        Text(
                                            text = "Preu per producte: ${formattedPrice}€",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.primary,
                                            modifier = modifier.padding(
                                                horizontal = 16.dp,
                                                vertical = 2.dp
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }

                }
            }
        } else {
            Text(
                text = "Ha ocurrido un error",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = modifier.padding(16.dp)
            )
        }
    }
}

@Preview
@Composable
fun PaymentScreenPreview(){
    MBADTheme {
        PaymentScreen()
    }
}