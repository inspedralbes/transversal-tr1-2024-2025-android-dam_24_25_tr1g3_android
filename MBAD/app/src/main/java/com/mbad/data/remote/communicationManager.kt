package com.mbad.data.remote

import com.google.gson.GsonBuilder
import com.mbad.data.model.Product
import com.mbad.data.OrderRequest
import com.mbad.data.OrderResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


private const val BASE_URL = "http://tr1g3.dam.inspedralbes.cat:28888"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
    .build()

interface ApiService {

    @GET("products")
    suspend fun getProducts(): List<Product>

    /*
    en @POST("order") hay que especificar la ruta,
    en este caso es "order"
    -
    orderProducts es el nombre que se le da para cuando queramos llamarlo
    ejempl: StoreApi.orderProducts(OrderProducts)
    -
    en (@Body: order: OrderRequest) se especifica el formato
    de los datos enviados en el Body
    -
    en Response<OrderResponse> el OrderResponse especifica el
    formato en el que trataremos la respuesta recibida por el servidor
     */
    @POST("order") // Cambia "order" al endpoint correcto
    suspend fun orderProducts(@Body order: OrderRequest): Response<OrderResponse>
}


object StoreApi {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}