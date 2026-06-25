package com.example.prototipo_pokemon.View.Components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.prototipo_pokemon.Modelo.PokemonResult

@Composable
fun PokemonCard(
    pokemon: PokemonResult,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onCard: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = {onCard()})
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            //Parte izquierda, imagen y nombre
            Row(verticalAlignment = Alignment.CenterVertically)
            {
                AsyncImage(
                    model = pokemon.imageURL,
                    contentDescription = pokemon.name,
                    modifier = Modifier.size(64.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Boton de Favorito",
                        tint = if (isFavorite) Color.Red else Color.LightGray
                    )
                }
            }
        }
    }
}