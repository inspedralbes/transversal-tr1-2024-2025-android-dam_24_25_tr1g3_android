package com.example.myapp.data

data class User(var id: Int, var nom: String, var correu: String, var telefon: Int, var contrasenya: String)

fun emptyUser(): User{
    return User(id = 0, nom = "", correu = "", telefon = 0, contrasenya = "")
}
