package com.mbad.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.mbad.R
import com.mbad.ui.StoreViewModel



@Composable
fun ProductCard(
    name: String,
    price: Double,
    stock: Int,
    image: String,
    modifier: Modifier = Modifier,
    quantity: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .padding(8.dp)
            .width(200.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Base URL del servidor
            val baseUrl = "http://tr1g3.dam.inspedralbes.cat:28888/assets/"

            // Construye la URL completa concatenando la base URL con el nombre del archivo
            val imageUrl = "$baseUrl$image"

            // Imagen del producto
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre del producto
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Precio del producto
            val formattedPrice = if (price % 1 == 0.0) {
                // Si es un número entero, no mostramos decimales
                String.format("%.0f", price)
            } else {
                // Si tiene decimales, usamos String.format para mostrar hasta 2 decimales
                String.format("%.2f", price).replace('.', ',') // Reemplaza el punto por una coma
            }

            Text(
                text = "Preu: $formattedPrice€",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Stock del producto
            Text(
                text = "Stock: $stock",
                style = MaterialTheme.typography.bodyMedium,
                color = if (stock > 0) Color.Green else Color.Red
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Contenedor para centrar el texto
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically, // Alinear verticalmente al centro
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onDecrease,
                    enabled = quantity > 0, // Habilitar solo si hay cantidad en el carrito
                    modifier = Modifier.weight(1f).fillMaxWidth()
                ) {
                    Text("-")
                }

                // Contenedor para centrar el texto
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp), // Añadir padding horizontal
                    contentAlignment = Alignment.Center // Centrar el contenido dentro del Box
                ) {
                    Text(
                        //text = quantity.toString(),
                        text = "${quantity}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Button(
                    onClick = onIncrease,
                    enabled = stock > quantity, // Habilitar solo si hay stock disponible
                    modifier = Modifier.weight(1f).fillMaxWidth()
                ) {
                    Text("+")
                }
            }
        }
    }
}




@Composable
fun ShopScreen(
    modifier: Modifier = Modifier,
    storeViewModel: StoreViewModel = viewModel(),
    onIncreaseProduct: (Int) -> Unit,
    onDecreaseProduct: (Int) -> Unit,
    onCartButtonClicked: () -> Unit,
){
    val storeUiState by storeViewModel.uiState.collectAsState()

    Box(modifier){
        Image(
            painter = painterResource(id = R.drawable.start_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.5F,
            modifier = Modifier.fillMaxSize()
        )
        Column(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f)
            ) {
                items(storeUiState.StoreProducts.size) { index ->
                    val product = storeUiState.StoreProducts[index]
                    var quantity = storeUiState.CartProducts.find{ it.ID_producto == product.id }?.cantidad ?: 0

                    ProductCard(
                        name = product.name,
                        price = product.price,
                        stock = product.stock,
                        image = product.image,
                        quantity = quantity,
                        onIncrease = { onIncreaseProduct(product.id) },
                        onDecrease = { onDecreaseProduct(product.id) },
                        modifier = Modifier
                    )
                }
            }
        }
        Button(
            onClick = onCartButtonClicked,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)

        ) {
            Text(text = "Carret")
        }
    }
}

@Preview
@Composable
fun ShopScreenPreview(){

    ProductCard(
        name = "product.name",
        price = 22.00,
        stock = 23,
        image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRWJuwr8vTqrxZXJAIbQXk3mUZM3l5o3881gQ&s",
        quantity = 0,
        onIncrease = {  },
        onDecrease = {  },
        modifier = Modifier

    )

}