package com.example.prototipo_pokemon

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.prototipo_pokemon.View.Navigation.AppEsqueleto
import com.example.prototipo_pokemon.View.Screens.BusquedaScreen
import com.example.prototipo_pokemon.View.Screens.FavoriteScreen
import com.example.prototipo_pokemon.View.Screens.HomeScreen
import com.example.prototipo_pokemon.ViewModel.BusquedaViewModel
import com.example.prototipo_pokemon.ViewModel.DetallesViewModel
import com.example.prototipo_pokemon.ViewModel.FavoritosViewModel
import com.example.prototipo_pokemon.ViewModel.HomeViewModel
import com.example.prototipo_pokemon.ui.theme.PrototipoPokemonTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    // Fábrica para crear el FavoritosViewModel inyectando el disco duro (SharedPreferences)
    // Esto es necesario porque ahora FavoritosViewModel pide las prefs por el constructor
    private val favoritosViewModel: FavoritosViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                // Obtenemos las SharedPreferences desde la Activity (que tiene el pasaporte/Context)
                val prefs = getSharedPreferences("mis_favoritos", Context.MODE_PRIVATE)
                return FavoritosViewModel(prefs) as T
            }
        }
    }

    private val homeViewModel: HomeViewModel by viewModels()
    private val busquedaViewModel: BusquedaViewModel by viewModels()
    private val detallesViewModel: DetallesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrototipoPokemonTheme {
                val navController = rememberNavController()


                AppEsqueleto(
                    navController = navController, 
                    homeViewModel = homeViewModel, 
                    busquedaViewModel = busquedaViewModel, 
                    favViewModel = favoritosViewModel,
                    detallesViewModel = detallesViewModel
                )
            }
        }
    }
}
