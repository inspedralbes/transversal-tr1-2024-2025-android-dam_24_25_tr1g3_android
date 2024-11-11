package com.mbad.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.mbad.AppScreen
import com.mbad.data.CartItem
import com.mbad.data.BuyUiState
import com.mbad.data.OrderRequest
import com.mbad.data.OrderResponse
import com.mbad.data.StoreUiState
import com.mbad.data.remote.StoreApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StoreViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StoreUiState())
    val uiState: StateFlow<StoreUiState> = _uiState.asStateFlow()

    private val _buyUiState = MutableStateFlow(BuyUiState())
    val buyUiState: StateFlow<BuyUiState> = _buyUiState.asStateFlow()

    fun addProductToCart(productId: Int) {
        _uiState.update { currentState ->
            // Buscamos el producto en `StoreProducts` para obtener los detalles
            val product = currentState.StoreProducts.find { it.id == productId }

            // Si el producto existe, creamos una nueva lista de CartProducts
            val updatedCartProducts = currentState.CartProducts.map { cartItem ->
                if (cartItem.ID_producto == productId) {
                    // Incrementar la cantidad si el ID coincide
                    cartItem.copy(cantidad = cartItem.cantidad + 1)
                } else {
                    cartItem
                }
            }.toMutableList()

            // Si el producto no estaba previamente en el pedido, lo agregamos
            if (updatedCartProducts.none { it.ID_producto == productId }) {
                product?.let {
                    updatedCartProducts.add(CartItem(
                        ID_producto = productId,
                        cantidad = 1,
                        precio_unitario = product.price
                    )
                    )
                }
            }

            // Actualizamos el estado con la nueva lista de productos en el pedido
            currentState.copy(CartProducts = updatedCartProducts)
        }
        println(_uiState.value.CartProducts)
    }

    fun removeProductFromCart(productId: Int) {
        _uiState.update { currentState ->
            // Crear una nueva lista de CartProducts
            val updatedCartProducts = currentState.CartProducts.map { cartItem ->
                if (cartItem.ID_producto == productId) {
                    // Disminuir la cantidad si es mayor que 1
                    if (cartItem.cantidad > 1) {
                        cartItem.copy(cantidad = cartItem.cantidad - 1)
                    } else {
                        null // Para eliminar el item
                    }
                } else {
                    cartItem
                }
            }.filterNotNull().toMutableList() // Filtrar los nulls que indican elementos eliminados

            // Devolver un nuevo estado con la lista de productos actualizada
            currentState.copy(CartProducts = updatedCartProducts)
        }
        println(_uiState.value.CartProducts)
    }

    fun sendOrder(navController: NavController){
        viewModelScope.launch {
            println("Posting order...")
            _buyUiState.update { currentState -> currentState.copy(isLoading = true) }

            val orderRequest = OrderRequest(
                ID_usuario = 12,
                fecha = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()),
                total_pedido = _uiState.value.CartProducts.sumOf { it.cantidad * it.precio_unitario },
                productos = _uiState.value.CartProducts
            )
            println(orderRequest)
            val response: Response<OrderResponse> = StoreApi.retrofitService.orderProducts(orderRequest)
            Log.d("EEEENDDDD","EEEENDDDD")

            if (response.isSuccessful) {
                val OrderResponse = response.body() ?: OrderResponse()
                _buyUiState.update { currentState -> currentState.copy(Success = true, orderResponse = OrderResponse) }
                navController.navigate(AppScreen.Payment.name)
                println("Orden realizada con éxito: ${OrderResponse?.num_pedido}")
                println(OrderResponse)
            } else {
                _buyUiState.update { currentState -> currentState.copy(Success = false) }
                Log.e("StoreViewModel", "Error en la solicitud: ${response.errorBody()?.string()}")
            }

            _uiState.update { currentState -> currentState.copy(CartProducts = emptyList()) }
            _buyUiState.update { currentState -> currentState.copy(isLoading = false) }
        }
    }


    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            println("Fetching products...")
            _uiState.update { currentState -> currentState.copy(isLoading = true) }

            // Obtener productos del API
            val storeProducts = StoreApi.retrofitService.getProducts()
            println("PRODUCTS ARRIVED")
            storeProducts.forEach { product ->
                println("ID: ${product.id}, Nombre: ${product.name}, Precio: ${product.price}, Imagen: ${product.image}")
            }

            _uiState.update { currentState ->
                currentState.copy(
                    isLoading = false,
                    StoreProducts = storeProducts
                )
            }
        }
    }
}
