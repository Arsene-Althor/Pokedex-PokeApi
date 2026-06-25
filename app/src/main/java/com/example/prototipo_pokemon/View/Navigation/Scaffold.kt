package com.example.prototipo_pokemon.View.Navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.prototipo_pokemon.View.Components.MyBottomBar
import com.example.prototipo_pokemon.View.Components.MyTopBar
import com.example.prototipo_pokemon.ViewModel.BusquedaViewModel
import com.example.prototipo_pokemon.ViewModel.DetallesViewModel
import com.example.prototipo_pokemon.ViewModel.FavoritosViewModel
import com.example.prototipo_pokemon.ViewModel.HomeViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppEsqueleto(
    navController: NavHostController,
    favViewModel: FavoritosViewModel,
    homeViewModel: HomeViewModel,
    busquedaViewModel: BusquedaViewModel,
    detallesViewModel: DetallesViewModel
) {

    // Observamos en qué pantalla estamos actualmente para marcar el icono correcto en la barra inferior
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val rutaActual = navBackStackEntry?.destination?.route

    // Estado para controlar si queremos ver la app a pantalla completa (sin barras)
    var isFullScreen by remember { mutableStateOf(false) }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val mostrarFabLista = rutaActual == AppRoutes.Home.ruta
            || rutaActual == AppRoutes.Buscar.ruta
            || rutaActual == AppRoutes.Favoritos.ruta

    // Scaffold es la plantilla de Material Design que organiza las piezas de la UI
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        // Barra superior: título, pantalla completa e información
        topBar = {
            MyTopBar(
                onNavigateToInfo = { navController.navigate(AppRoutes.Info.ruta) },
                isFullScreen = isFullScreen,
                onToggleFullScreen = { isFullScreen = !isFullScreen }
            )
        },
        // Barra inferior: botones para navegar entre Home, Buscar y Favoritos
        bottomBar = {
            if (!isFullScreen) {
                MyBottomBar(navController = navController, rutaActual = rutaActual)
            }
        },
        // Botón flotante: vuelve al inicio de la lista (Home, Buscar, Favoritos)
        floatingActionButton = {
            if (!isFullScreen && mostrarFabLista) {
                FloatingActionButton(
                    onClick = {
                        scope.launch { listState.animateScrollToItem(0) }
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "Ir al inicio de la lista"
                    )
                }
            }
        }
    ) { innerPadding ->
        // Aquí es donde se "inyecta" el contenido de cada pantalla (Home, Buscar, etc.)
        // innerPadding asegura que el contenido no quede tapado por las barras
        AppNavigation(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            favViewModel = favViewModel,
            homeViewModel = homeViewModel,
            busquedaViewModel = busquedaViewModel,
            detallesViewModel = detallesViewModel,
            listState = listState
        )
    }
}

//Ventana temporal para que la navegacion funcione
@Composable
fun PantallaFalsa(titulo: String){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = titulo, style = MaterialTheme.typography.headlineSmall)
    }
}
