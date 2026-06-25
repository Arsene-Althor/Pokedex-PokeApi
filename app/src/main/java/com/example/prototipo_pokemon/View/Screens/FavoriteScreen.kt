package com.example.prototipo_pokemon.View.Screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.prototipo_pokemon.View.Components.PokemonCard
import com.example.prototipo_pokemon.View.Navigation.AppRoutes
import com.example.prototipo_pokemon.ViewModel.FavoritosViewModel

@Composable
fun FavoriteScreen(
    favViewModel: FavoritosViewModel,
    navController: NavController,
    listState: LazyListState
) {

    val favoritos by favViewModel.favoriteList.collectAsState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        items(favoritos) { pokemon ->
            PokemonCard(
                pokemon = pokemon,
                isFavorite = favoritos.contains(pokemon),
                onFavoriteClick = {
                    favViewModel.ToggleFavorito(pokemon = pokemon)
                },
                onCard = {
                    navController.navigate(AppRoutes.Detalles.crearRuta(pokemon.name))
                }
            )

            HorizontalDivider()
        }
    }

}