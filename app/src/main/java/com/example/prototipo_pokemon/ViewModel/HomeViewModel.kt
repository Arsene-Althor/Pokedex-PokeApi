package com.example.prototipo_pokemon.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prototipo_pokemon.Modelo.PokemonResult
import com.example.prototipo_pokemon.Service.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository = PokemonRepository()


    private val _pokemonList = MutableStateFlow<List<PokemonResult>>(emptyList())

    val pokemonList: StateFlow<List<PokemonResult>> = _pokemonList.asStateFlow()

    private fun ObtenerLista() {
        viewModelScope.launch {

            _pokemonList.value = repository.obtenerPokemons()

        }
    }

    init {
        ObtenerLista()
    }
}