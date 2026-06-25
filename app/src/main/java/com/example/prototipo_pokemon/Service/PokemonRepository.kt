package com.example.prototipo_pokemon.Service

import android.util.Log
import com.example.prototipo_pokemon.Modelo.PokemonDetails
import com.example.prototipo_pokemon.Modelo.PokemonResult
import com.example.prototipo_pokemon.Modelo.PokemonSpecies

class PokemonRepository (){

    private var pokemonCache = emptyList<PokemonResult>()
    // Usamos un mapa (diccionario) para guardar Nombre -> Detalles
    private val detallesCache = mutableMapOf<String, PokemonDetails>()
    // Igual que arriba, pero para la especie (texto Pokédex, generación, etc.)
    private val especieCache = mutableMapOf<String, PokemonSpecies>()

    suspend fun obtenerPokemons(): List<PokemonResult>{
        try {
            if (pokemonCache.isEmpty()){
                val respuesta = RetrofitClient.api.obtenerListaPokemon()

                pokemonCache = respuesta.listaPokemons
                return respuesta.listaPokemons
            } else {
                return pokemonCache
            }

        } catch (e: Exception){
            Log.e("Quiero_explotar", "Error al descargar: ${e.message}")
            return emptyList()
        }
    }

    suspend fun getPokemonDetails(nombre: String): PokemonDetails {
        try {
            //Revisamos si ya tenemos guardado a este Pokémon en el diccionario
            if (detallesCache.containsKey(nombre)) {
                return detallesCache[nombre]!! // Lo devolvemos desde la caché
            } else {

                val respuesta = RetrofitClient.api.getPokemonDetails(nombre)

                detallesCache[nombre] = respuesta
                return respuesta
            }
        } catch (e: Exception) {
            Log.e("Quiero_explotar", "Error al descargar detalles: ${e.message}")
            throw e
        }
    }

    suspend fun obtenerEspecie(nombre: String): PokemonSpecies {
        try {
            if (especieCache.containsKey(nombre)) {
                return especieCache[nombre]!!
            }
            val respuesta = RetrofitClient.api.obtenerEspecie(nombre)
            especieCache[nombre] = respuesta
            return respuesta
        } catch (e: Exception){
            Log.e("Quiero_explotar", "Error al descargar especie: ${e.message}")
            throw e
        }
    }
}