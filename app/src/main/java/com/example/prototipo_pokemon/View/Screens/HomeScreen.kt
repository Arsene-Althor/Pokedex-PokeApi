package com.example.prototipo_pokemon.View.Screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.prototipo_pokemon.View.Components.PokemonCard
import com.example.prototipo_pokemon.View.Navigation.AppRoutes
import com.example.prototipo_pokemon.ViewModel.FavoritosViewModel
import com.example.prototipo_pokemon.ViewModel.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    favViewModel: FavoritosViewModel,
    navController: NavController,
    listState: LazyListState
) {

    //Nos suscribimos
    val listaPokemon by homeViewModel.pokemonList.collectAsState()

    val favoritos by favViewModel.favoriteList.collectAsState()


    //LazyColumn para que no cargue todo de golpe
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
    ) {
        //items: toma la lista kotlin y crea un Text por cada Pokémon
        items(listaPokemon) { pokemon ->
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
            HorizontalDivider() //Crea una linea gris debajo de cada nombre
        }
    }
}