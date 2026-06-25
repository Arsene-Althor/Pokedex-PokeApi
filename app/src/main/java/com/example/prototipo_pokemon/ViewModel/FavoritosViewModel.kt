package com.example.prototipo_pokemon.ViewModel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.prototipo_pokemon.Modelo.PokemonResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Ahora es un ViewModel normal y recibe ya las preferencias (el disco duro) por parámetro
class FavoritosViewModel(private val prefs: SharedPreferences) : ViewModel() {

    private var _favoriteList = MutableStateFlow<List<PokemonResult>>(emptyList())
    var favoriteList: StateFlow<List<PokemonResult>> = _favoriteList.asStateFlow()

    init {
        // Leemos la nueva llave (le llamo "datos" para que ignore los malos que guardamos antes)
        val datosGuardados = prefs.getStringSet("datos", emptySet()) ?: emptySet()

        val listaReconstruida = datosGuardados.map { textoPegado ->
            // Cortamos el texto por la mitad usando el palito
            val partes = textoPegado.split("|")

            // partes[0] es el nombre, partes[1] es la URL real
            PokemonResult(name = partes[0], url = partes[1])
        }

        _favoriteList.value = listaReconstruida
    }

    fun ToggleFavorito(pokemon: PokemonResult){

        //Miramos que tenemos en memoria
        val listaActual = _favoriteList.value

        if (listaActual.contains(pokemon)){
            //Si existe, creamos una lista nueva restando ese pokemon
            _favoriteList.value = listaActual - pokemon
        } else {
            //si no existe, creamos una lista nueva sumando ese pokemon
            _favoriteList.value = listaActual + pokemon
        }

        // Pegamos el nombre y la url separados por un | para guardarlo en el disco duro
        val datosParaGuardar = _favoriteList.value.map { "${it.name}|${it.url}" }.toSet()
        prefs.edit().putStringSet("datos", datosParaGuardar).apply()
    }
}
