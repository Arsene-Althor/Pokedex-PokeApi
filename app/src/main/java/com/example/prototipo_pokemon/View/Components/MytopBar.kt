package com.example.prototipo_pokemon.View.Components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.prototipo_pokemon.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(
    onNavigateToInfo: () -> Unit,
    isFullScreen: Boolean,
    onToggleFullScreen: () -> Unit
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Mi Pokedex")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            // Pantalla completa: oculta la barra inferior para ver más lista
            IconButton(onClick = onToggleFullScreen) {
                Icon(
                    imageVector = if (isFullScreen) Icons.Default.Clear else Icons.Default.Menu,
                    contentDescription = if (isFullScreen) "Salir pantalla completa" else "Pantalla completa"
                )
            }
            IconButton(onClick = onNavigateToInfo) {
                Icon(Icons.Default.Info, contentDescription = "Información")
            }
        }
    )
}