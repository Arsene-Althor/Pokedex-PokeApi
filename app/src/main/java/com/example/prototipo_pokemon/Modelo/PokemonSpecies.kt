package com.example.prototipo_pokemon.Modelo

import com.google.gson.annotations.SerializedName

// Datos de la especie del Pokémon (descripciones y generación)
data class PokemonSpecies(
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<FlavorText>,
    val generation: GenerationData
)

// Una entrada de texto descriptivo en la Pokédex
data class FlavorText(
    @SerializedName("flavor_text")
    val text: String,
    val language: LanguageData
)

// Datos del idioma de la descripción
data class LanguageData(val name: String)

// Datos de la generación a la que pertenece
data class GenerationData(val name: String)
