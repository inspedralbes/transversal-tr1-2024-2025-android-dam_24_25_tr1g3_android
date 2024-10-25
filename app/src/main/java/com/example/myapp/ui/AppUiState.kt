package com.example.myapp.ui

/**
 * Data class that represents the game UI state
 */
data class AppUiState(
    val productesSeleccionats: Array<String> = emptyArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AppUiState

        return productesSeleccionats.contentEquals(other.productesSeleccionats)
    }

    override fun hashCode(): Int {
        return productesSeleccionats.contentHashCode()
    }
}
