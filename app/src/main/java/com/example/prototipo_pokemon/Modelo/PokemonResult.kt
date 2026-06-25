package com.example.prototipo_pokemon.Modelo

// Representa un solo Pokémon de la lista general (nombre y link a sus detalles)
data class PokemonResult(
    val name: String,
    val url: String
){
    // Propiedad calculada para obtener la URL de la imagen frontal del Pokémon
    val imageURL: String
        get() {
            // Descomponemos la URL usando la '/' para extraer el ID
            val pedazos = url.split("/")

            // Filtramos para quitar espacios y nos quedamos con el último valor (el ID)
            val id = pedazos.filter { it.isNotEmpty() }.last()

            // Inyectamos el ID en la dirección oficial de sprites
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
        }
}
