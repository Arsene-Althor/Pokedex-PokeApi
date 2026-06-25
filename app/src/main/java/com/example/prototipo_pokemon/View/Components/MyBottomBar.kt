package com.example.prototipo_pokemon.View.Components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.prototipo_pokemon.View.Navigation.AppRoutes

@Composable
fun MyBottomBar(
    navController: NavHostController,
    rutaActual: String?
) {
    NavigationBar {
        //Boton Inicio
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = rutaActual == AppRoutes.Home.ruta,
            onClick = { navController.navigate(AppRoutes.Home.ruta) }
        )
        //Boton Buscar
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            label = { Text("Buscar") },
            selected = rutaActual == AppRoutes.Buscar.ruta,
            onClick = { navController.navigate(AppRoutes.Buscar.ruta) }
        )
        //Boton Favoritos
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos") },
            label = { Text("Favoritos") },
            selected = rutaActual == AppRoutes.Favoritos.ruta,
            onClick = { navController.navigate(AppRoutes.Favoritos.ruta) }
        )
    }
}