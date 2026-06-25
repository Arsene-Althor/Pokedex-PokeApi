package com.example.prototipo_pokemon.Service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

//Se usa object ya que queremos que solo exista una instacia de conexion en toda la app
object RetrofitClient {

    //Herramienta para llamar a internet
    val api: PokeApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/") //Ruta URL de la api
            .addConverterFactory(GsonConverterFactory.create()) //El traductor JSON
            .build()
            .create(PokeApiService::class.java) //Le pasamos la clase creada antes
    }
}