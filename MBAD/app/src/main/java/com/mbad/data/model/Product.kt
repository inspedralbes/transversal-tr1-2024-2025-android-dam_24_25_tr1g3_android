package com.mbad.data.model

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("ID_producto") val id: Int,
    @SerializedName("imagen") val image: String,
    @SerializedName("nombre") val name: String,
    @SerializedName("descripcion") val description: String,
    @SerializedName("precio") val price: Double,
    @SerializedName("stock") val stock: Int
)
