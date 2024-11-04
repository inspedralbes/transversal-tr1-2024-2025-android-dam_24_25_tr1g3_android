package com.example.myapp.data

data class Product(
    var id: Int,
    var nom: String,
    var linkimatge: String,
    var preu: Float,
    var stock: Int,
    var cantitatCompra: Int = 0,
    var select: Boolean = false
)