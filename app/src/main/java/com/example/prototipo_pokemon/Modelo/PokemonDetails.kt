package com.example.prototipo_pokemon.Modelo

import com.google.gson.annotations.SerializedName

// Detalles técnicos y estadísticas de un Pokémon específico
data class PokemonDetails(
    val id: Int,
    val weight: Int,
    val height: Int,
    val types: List<TypeSlot>,
    val stats: List<StatSlot>
) {
    // Genera la URL de la imagen oficial de alta calidad usando el ID
    val officialImageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"
}

// Contenedor para el tipo de Pokémon (Fuego, Agua, etc.)
data class TypeSlot(
    val type: TypeData
)

// Datos básicos del tipo
data class TypeData(
    val name: String
)

// Contenedor para una estadística base (HP, Ataque, etc.)
data class StatSlot(
    @SerializedName("base_stat") val baseStat: Int,
    val stat: StatData
)

// Datos básicos de la estadística
data class StatData(
    val name: String
)
