package com.example.prototipo_pokemon.View.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.prototipo_pokemon.View.Components.PokemonCard
import com.example.prototipo_pokemon.View.Navigation.AppRoutes
import com.example.prototipo_pokemon.ViewModel.BusquedaViewModel
import com.example.prototipo_pokemon.ViewModel.FavoritosViewModel

// Etiqueta en español para la UI; valorParaFiltro = nombre del tipo en la API (inglés, minúsculas).
private data class OpcionTipo(val etiqueta: String, val valorParaFiltro: String)

private val opcionesTipo = listOf(
    OpcionTipo("Todos los tipos", ""),
    OpcionTipo("Normal", "normal"),
    OpcionTipo("Fuego", "fire"),
    OpcionTipo("Agua", "water"),
    OpcionTipo("Eléctrico", "electric"),
    OpcionTipo("Planta", "grass"),
    OpcionTipo("Hielo", "ice"),
    OpcionTipo("Lucha", "fighting"),
    OpcionTipo("Veneno", "poison"),
    OpcionTipo("Tierra", "ground"),
    OpcionTipo("Volador", "flying"),
    OpcionTipo("Psíquico", "psychic"),
    OpcionTipo("Bicho", "bug"),
    OpcionTipo("Roca", "rock"),
    OpcionTipo("Fantasma", "ghost"),
    OpcionTipo("Dragón", "dragon"),
    OpcionTipo("Siniestro", "dark"),
    OpcionTipo("Acero", "steel"),
    OpcionTipo("Hada", "fairy")
)

// Cada opción muestra nombre amigable y envía al ViewModel el número que ya entiende la API (1..9) o vacío.
private data class OpcionGeneracion(val etiqueta: String, val valorParaFiltro: String)

private val opcionesGeneracion = listOf(
    OpcionGeneracion("Todas las regiones", ""),
    OpcionGeneracion("Kanto", "1"),
    OpcionGeneracion("Johto", "2"),
    OpcionGeneracion("Hoenn", "3"),
    OpcionGeneracion("Sinnoh", "4"),
    OpcionGeneracion("Teselia", "5"),
    OpcionGeneracion("Kalos", "6"),
    OpcionGeneracion("Alola", "7"),
    OpcionGeneracion("Galar", "8"),
    OpcionGeneracion("Paldea", "9")
)

@Composable
fun BusquedaScreen(
    busquedaViewModel: BusquedaViewModel,
    favViewModel: FavoritosViewModel,
    navController: NavHostController,
    listState: LazyListState
) {
    val listaFiltrada by busquedaViewModel.pokemonList.collectAsState()
    val mensajeError by busquedaViewModel.errorMensaje.collectAsState()
    val filtrando by busquedaViewModel.filtrando.collectAsState()
    val favoritos by favViewModel.favoriteList.collectAsState()

    var textoNombre by remember { mutableStateOf("") }
    var textoInicio by remember { mutableStateOf("") }
    var textoFin by remember { mutableStateOf("") }
    var generacionElegida by remember { mutableStateOf(opcionesGeneracion.first()) }
    var menuGeneracionAbierto by remember { mutableStateOf(false) }

    var tipoElegido by remember { mutableStateOf(opcionesTipo.first()) }
    var menuTipoAbierto by remember { mutableStateOf(false) }


    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        item {
            Text(
                text = "Filtros",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        item {
            OutlinedTextField(
                value = textoNombre,
                onValueChange = { textoNombre = it },
                label = { Text("Buscar por Nombre") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            )
        }

        item {
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = "Rango de ID (opcional)",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = textoInicio,
                        onValueChange = { textoInicio = it },
                        label = { Text("ID Desde") },
                        placeholder = { Text("Ej: 1") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = textoFin,
                        onValueChange = { textoFin = it },
                        label = { Text("ID Hasta") },
                        placeholder = { Text("Ej: 151") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = "Tipo",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = { menuTipoAbierto = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = tipoElegido.etiqueta,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Abrir lista de tipos"
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = menuTipoAbierto,
                        onDismissRequest = { menuTipoAbierto = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        opcionesTipo.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion.etiqueta) },
                                onClick = {
                                    tipoElegido = opcion
                                    menuTipoAbierto = false
                                }
                            )
                        }
                    }
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                Text(
                    text = "Generación / región",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Box(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = { menuGeneracionAbierto = true },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = generacionElegida.etiqueta,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Abrir lista de regiones"
                            )
                        }
                    }
                    DropdownMenu(
                        expanded = menuGeneracionAbierto,
                        onDismissRequest = { menuGeneracionAbierto = false },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        opcionesGeneracion.forEach { opcion ->
                            DropdownMenuItem(
                                text = { Text(opcion.etiqueta) },
                                onClick = {
                                    generacionElegida = opcion
                                    menuGeneracionAbierto = false
                                }
                            )
                        }
                    }
                }
            }
        }

        item {
            Button(
                onClick = {
                    busquedaViewModel.filtrarPokemon(
                        nombre = textoNombre,
                        idInicio = textoInicio,
                        idFin = textoFin,
                        textoTipo = tipoElegido.valorParaFiltro,
                        textoGeneracion = generacionElegida.valorParaFiltro
                    )
                },
                enabled = !filtrando,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
            ) {
                Text("Aplicar Filtros")
            }
        }

        item {
            if (filtrando) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Aplicando filtros…")
                }
            }
        }

        item {
            mensajeError?.let { msg ->
                Text(
                    text = msg,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }

        item {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = "Resultados (${listaFiltrada.size})",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        items(listaFiltrada, key = { it.name }) { pokemon ->
            PokemonCard(
                pokemon = pokemon,
                isFavorite = favoritos.contains(pokemon),
                onFavoriteClick = { favViewModel.ToggleFavorito(pokemon = pokemon) },
                onCard = {
                    navController.navigate(AppRoutes.Detalles.crearRuta(pokemon.name))
                }
            )
            HorizontalDivider()
        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
