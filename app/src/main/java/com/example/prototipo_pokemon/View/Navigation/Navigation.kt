package com.example.prototipo_pokemon.View.Navigation

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.prototipo_pokemon.View.Screens.BusquedaScreen
import com.example.prototipo_pokemon.View.Screens.DetallesScreen
import com.example.prototipo_pokemon.View.Screens.FavoriteScreen
import com.example.prototipo_pokemon.View.Screens.HomeScreen
import com.example.prototipo_pokemon.View.Screens.InfoScreen
import com.example.prototipo_pokemon.ViewModel.BusquedaViewModel
import com.example.prototipo_pokemon.ViewModel.DetallesViewModel
import com.example.prototipo_pokemon.ViewModel.FavoritosViewModel
import com.example.prototipo_pokemon.ViewModel.HomeViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    favViewModel: FavoritosViewModel,
    homeViewModel: HomeViewModel,
    busquedaViewModel: BusquedaViewModel,
    detallesViewModel: DetallesViewModel,
    listState: LazyListState
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.Home.ruta,
        modifier = modifier
    ) {
        composable(AppRoutes.Home.ruta){
            HomeScreen(
                homeViewModel = homeViewModel,
                favViewModel = favViewModel,
                navController = navController,
                listState = listState
            )
        }
        composable(AppRoutes.Buscar.ruta){
            BusquedaScreen(
                busquedaViewModel = busquedaViewModel,
                favViewModel = favViewModel,
                navController = navController,
                listState = listState
            )
        }
        composable(AppRoutes.Favoritos.ruta){
            FavoriteScreen(
                favViewModel = favViewModel,
                navController = navController,
                listState = listState
            )
        }
        composable(
            route = AppRoutes.Detalles.ruta,
            arguments = listOf(navArgument("pokemonName") { type = NavType.StringType })
        ) { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString("pokemonName") ?: ""
            DetallesScreen(nombrePokemon = pokemonName, viewModel = detallesViewModel)
        }
        composable(AppRoutes.Info.ruta){
            InfoScreen()
        }
    }
}