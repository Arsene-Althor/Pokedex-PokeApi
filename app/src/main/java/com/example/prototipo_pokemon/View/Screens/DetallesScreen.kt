package com.example.prototipo_pokemon.View.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.prototipo_pokemon.ViewModel.DetallesViewModel

@Composable
fun DetallesScreen(
    nombrePokemon: String,
    viewModel: DetallesViewModel
) {
    LaunchedEffect(nombrePokemon) {
        viewModel.buscarPokemonDetails((nombrePokemon))
    }

    // Observamos AMBOS flujos de datos
    val detalles by viewModel.pokemonDetails.collectAsState()
    val especie by viewModel.pokemonSpecies.collectAsState() // NUEVO

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = nombrePokemon.uppercase(),
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Verificamos que AMBOS datos hayan llegado antes de dibujar
        if (detalles == null || especie == null) {
            CircularProgressIndicator(modifier = Modifier.padding(32.dp))
        } else {
            // Mostrar los Tipos
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 16.dp)) {
                detalles!!.types.forEach { tipo ->
                    SuggestionChip(
                        onClick = { },
                        label = { Text(tipo.type.name.uppercase()) }
                    )
                }
            }

            AsyncImage(
                model = detalles!!.officialImageUrl,
                contentDescription = nombrePokemon,
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp)
            )

            Card(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(text = "Peso: ${detalles!!.weight / 10.0} kg", fontWeight = FontWeight.Bold)
                        Text(text = "Altura: ${detalles!!.height / 10.0} m", fontWeight = FontWeight.Bold)
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    // Mostrar Generación y Descripción
                    Text(
                        text = "Región/Generación: ${especie!!.generation.name.uppercase()}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Buscamos en español, si no hay, buscamos el primero que exista, y si la lista está vacía, ponemos un texto por defecto.
                    val descripcion = especie!!.flavorTextEntries.firstOrNull { it.language.name == "es" }?.text
                        ?: especie!!.flavorTextEntries.firstOrNull()?.text
                        ?: "Descripción no disponible en la Pokédex."

                    Text(
                        text = descripcion.replace("\n", " ").replace("\u000c", " "),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Text(
                text = "Estadísticas Base",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            detalles!!.stats.forEach { statSlot ->
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = statSlot.stat.name.uppercase())
                    Text(text = statSlot.baseStat.toString(), fontWeight = FontWeight.Bold)
                }
                HorizontalDivider()
            }
        }
    }
}