package com.example.prototipo_pokemon.Service

import com.example.prototipo_pokemon.Modelo.PokemonDetails
import com.example.prototipo_pokemon.Modelo.PokemonResponse
import com.example.prototipo_pokemon.Modelo.PokemonSpecies
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApiService {

    //Le hacemos una peticion GET a su ruta exacta
    @GET("pokemon?limit=1025")

    suspend fun obtenerListaPokemon(): PokemonResponse

    @GET("pokemon/{name}")
    suspend fun getPokemonDetails(@Path("name") nombre: String): PokemonDetails

    @GET("pokemon-species/{name}")
    suspend fun obtenerEspecie(@Path("name") nombre: String): PokemonSpecies
}