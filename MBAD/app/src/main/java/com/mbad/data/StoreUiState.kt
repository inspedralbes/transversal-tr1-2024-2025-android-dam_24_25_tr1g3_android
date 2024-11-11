package com.mbad.data

import com.mbad.data.model.Product

data class StoreUiState(
    val StoreProducts: List<Product> = emptyList(),
    val CartProducts: List<CartItem> = emptyList(),
    var isLoading: Boolean = true,
)

data class CartItem(
    var ID_producto: Int,
    var cantidad: Int,
    var precio_unitario: Double

)

data class BuyUiState(
    var Success: Boolean = false,
    var orderResponse: OrderResponse = OrderResponse(),
    var isLoading: Boolean = true,
)



// REQUEST
data class OrderRequest(
    val ID_usuario: Int,
    val fecha: String,
    val total_pedido: Double,
    val estado: String = "Pendiente",
    val productos: List<CartItem> = emptyList()
)

// REPOSNSE
data class OrderResponse(
    val num_pedido: Int = 0,
    val ID_usuario: Int = 0,
    val fecha: String = "",
    val total_pedido: Double = 0.0,
    val estado: String = "Pendiente",
    val productos: List<CartItem> = emptyList()
)