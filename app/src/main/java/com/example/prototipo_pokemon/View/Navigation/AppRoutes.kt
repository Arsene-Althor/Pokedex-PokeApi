package com.example.prototipo_pokemon.View.Navigation

import android.R

sealed class AppRoutes (val ruta: String) {
    data object Home : AppRoutes("home")
    data object Buscar : AppRoutes("buscar")
    data object Favoritos : AppRoutes("favoritos")
    data object Detalles: AppRoutes("detalles/{pokemonName}"){
        fun crearRuta(nombre: String) = "detalles/$nombre"
    }
    data object Info : AppRoutes("info")
}