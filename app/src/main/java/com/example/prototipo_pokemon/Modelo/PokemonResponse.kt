package com.example.prototipo_pokemon.Modelo

import com.google.gson.annotations.SerializedName

// Representa la respuesta completa de la API al pedir la lista de Pokémon
data class PokemonResponse(
    @SerializedName("results")
    val listaPokemons: List<PokemonResult>
)
