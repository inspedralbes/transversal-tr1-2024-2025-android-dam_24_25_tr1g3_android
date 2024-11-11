package com.mbad.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mbad.R
import com.mbad.data.CartItem
import com.mbad.ui.StoreViewModel


@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    storeViewModel: StoreViewModel = viewModel(),
    onIncreaseProduct: (Int) -> Unit,
    onDecreaseProduct: (Int) -> Unit,
    onPayClick: () -> Unit
) {
    val storeUiState by storeViewModel.uiState.collectAsState()
    val orderProducts = storeUiState.CartProducts

    // Calcular el total de la orden
    val totalPrice = orderProducts.sumOf { it.cantidad * it.precio_unitario }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.start_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.5F,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Lista de productos en el pedido con scroll si es necesario
            if (orderProducts.isEmpty()) {
                Text(
                    text = "No hay productos en el pedido.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = modifier.padding(16.dp)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f) // Permite que el listado de productos sea desplazable
                        .padding(bottom = 16.dp)
                ) {
                    items(orderProducts.size) { index ->
                        val cartItem = orderProducts[index]
                        storeUiState.StoreProducts.find { it.id == cartItem.ID_producto }?.let {
                            OrderProductCard(
                                modifier = Modifier,
                                cartItem,
                                it.stock,
                                onIncrease = { onIncreaseProduct(cartItem.ID_producto) },
                                onDecrease = { onDecreaseProduct(cartItem.ID_producto) },
                            )
                        }
                    }
                }

                // Resumen del pedido
                OrderSummary(
                    totalPrice = totalPrice,
                    onPayClick = onPayClick
                )
            }
        }
    }
}



@Composable
fun OrderSummary(totalPrice: Double, onPayClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Mostrar el total del pedido
            Text(
                text = "Total: ${String.format("%.2f", totalPrice).replace('.', ',')}€",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Botón de pagar
            Button(
                onClick = onPayClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Text(text = "Pagar")
            }
        }
    }
}


@Composable
fun OrderProductCard(
    modifier: Modifier = Modifier,
    cartItem: CartItem,
    stock: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    val formattedPrice = String.format("%.2f", cartItem.precio_unitario).replace('.', ',')

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp) // Espaciado entre elementos
                .padding(16.dp) // Padding interno del Card
                .fillMaxWidth()
        ) {
            Text(
                text = "Producto ID: ${cartItem.ID_producto}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Cantidad: ${cartItem.cantidad}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Precio unitario: $formattedPrice€",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Contenedor para los botones de añadir y eliminar
            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = onDecrease
                ) {
                    Text("-") // Botón para añadir
                }
                Button(
                    onClick = onIncrease,
                    enabled = stock > cartItem.cantidad,
                ) {
                    Text("+") // Botón para eliminar
                }
            }
        }
    }


}
