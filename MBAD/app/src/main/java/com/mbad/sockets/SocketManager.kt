package com.mbad.sockets

import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import android.util.Log

class SocketManager {

    private lateinit var socket: Socket

    init {
        connectSocket()
    }

    private fun connectSocket() {
        try {
            socket = IO.socket("http://tr1g3.dam.inspedralbes.cat:28888") // Cambia la URL según tu configuración
            socket.connect()

            // Escuchar eventos
            socket.on(Socket.EVENT_CONNECT, Emitter.Listener {
                Log.d("SocketIO", "Conectado al servidor")
                socket.emit("mensaje", "Hola desde Android")
            })

            socket.on("mensaje", Emitter.Listener { args ->
                Log.d("SocketIO", "Mensaje recibido: ${args[0]}")
            })

            socket.on(Socket.EVENT_DISCONNECT, Emitter.Listener {
                Log.d("SocketIO", "Desconectado del servidor")
            })

        } catch (e: Exception) {
            Log.e("SocketIO", "Error al conectar: ${e.message}")
        }
    }

    fun sendMessage(message: String) {
        socket.emit("mensaje", message)
    }
}
