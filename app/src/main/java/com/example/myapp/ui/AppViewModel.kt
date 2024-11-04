package com.example.myapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapp.data.Product
import com.example.myapp.data.User
import com.example.myapp.data.emptyUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel  : ViewModel(){

    // Llista de productes
    private val _productes = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _productes.asStateFlow()

    // Llista de productes seleccionats
    private val _productesSeleccionats = MutableStateFlow<List<Product>>(emptyList())
    val productesSeleccionats: StateFlow<List<Product>> get() = _productesSeleccionats.asStateFlow()

    private val _user = MutableStateFlow<User>(emptyUser())
    val user: StateFlow<User> get() = _user.asStateFlow()

    // Funció per establir la llista de productes
    fun setProductes(array: List<Product>) {
        _productes.value = array
        actualitzarProductesSeleccionats()
    }

    // Funció per actualitzar la llista de productes seleccionats
    private fun actualitzarProductesSeleccionats() {
        _productesSeleccionats.value = _productes.value.filter { it.select }
    }

    fun setUser(user: User) {
        _user.value = user
        println(this.user)
    }

}