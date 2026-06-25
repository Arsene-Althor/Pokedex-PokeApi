package com.example.prototipo_pokemon.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prototipo_pokemon.Modelo.PokemonDetails
import com.example.prototipo_pokemon.Modelo.PokemonSpecies
import com.example.prototipo_pokemon.Service.PokemonRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetallesViewModel : ViewModel() {

    private val repository = PokemonRepository()

    private val _pokemonDetails = MutableStateFlow<PokemonDetails?>(null)
    val pokemonDetails: StateFlow<PokemonDetails?> = _pokemonDetails

    private val _pokemonSpecies = MutableStateFlow<PokemonSpecies?>(null)
    val pokemonSpecies: StateFlow<PokemonSpecies?> = _pokemonSpecies

    fun buscarPokemonDetails(nombre: String) {
        viewModelScope.launch {
            try {
                _pokemonDetails.value = null
                _pokemonSpecies.value = null

                delay(1500L)

                _pokemonDetails.value = repository.getPokemonDetails(nombre)
                _pokemonSpecies.value = repository.obtenerEspecie(nombre)
            } catch (e: Exception) {
                println("Error al obtener detalles: ${e.message}")
            }
        }
    }
}