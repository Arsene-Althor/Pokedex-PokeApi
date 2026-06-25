package com.example.prototipo_pokemon.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prototipo_pokemon.Modelo.PokemonResult
import com.example.prototipo_pokemon.Service.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BusquedaViewModel : ViewModel() {
    private val repository = PokemonRepository()

    // Lista Maestra
    private var listaOriginal = emptyList<PokemonResult>()

    // Lista Filtrada (Lo que ve la UI)
    private val _pokemonList = MutableStateFlow<List<PokemonResult>>(emptyList())
    val pokemonList: StateFlow<List<PokemonResult>> = _pokemonList.asStateFlow()

    // Estados de los filtros y errores
    private val _errorMensaje = MutableStateFlow<String?>(null)
    val errorMensaje: StateFlow<String?> = _errorMensaje.asStateFlow()

    // True mientras se consultan tipos/generación en la API (puede tardar un poco)
    private val _filtrando = MutableStateFlow(false)
    val filtrando: StateFlow<Boolean> = _filtrando.asStateFlow()

    init {
        obtenerLista()
    }

    private fun obtenerLista() {
        viewModelScope.launch {
            try {
                listaOriginal = repository.obtenerPokemons()
                _pokemonList.value = listaOriginal
            } catch (e: Exception) {
                _errorMensaje.value = "Error al conectar con la API"
            }
        }
    }

    // Aplica nombre + rango de ID opcional en memoria (si ambos ID van vacíos, no se filtra por número).
    // Si hay tipo o generación, consulta API por cada candidato (caché en repositorio).
    fun filtrarPokemon(
        nombre: String,
        idInicio: String,
        idFin: String,
        textoTipo: String,
        textoGeneracion: String
    ) {
        viewModelScope.launch {
            _filtrando.value = true
            try {
                _errorMensaje.value = null

                val inicioStr = idInicio.trim()
                val finStr = idFin.trim()
                val rangoIds: IntRange? = when {
                    inicioStr.isEmpty() && finStr.isEmpty() -> null
                    inicioStr.isEmpty() || finStr.isEmpty() -> {
                        _errorMensaje.value =
                            "Rellena ID desde y hasta, o deja ambos vacíos para no filtrar por ID"
                        return@launch
                    }
                    else -> {
                        val inicio = inicioStr.toIntOrNull()
                        val fin = finStr.toIntOrNull()
                        if (inicio == null || fin == null) {
                            _errorMensaje.value = "Escribe números válidos en ID desde y hasta"
                            return@launch
                        }
                        if (inicio < 1 || fin < 1) {
                            _errorMensaje.value = "Los IDs deben ser 1 o mayores"
                            return@launch
                        }
                        if (inicio > fin) {
                            _errorMensaje.value = "El inicio no puede ser mayor al fin"
                            return@launch
                        }
                        inicio..fin
                    }
                }

                val tipoNormalizado = textoTipo.trim().lowercase()
                if (tipoNormalizado.isNotEmpty() && tipoNormalizado !in TIPOS_VALIDOS) {
                    _errorMensaje.value =
                        "Tipo no reconocido. Usa el nombre en inglés (ej: fire, water)"
                    return@launch
                }

                val generacionUsuario = textoGeneracion.trim()
                var numeroGeneracionFiltro: Int? = null
                if (generacionUsuario.isNotEmpty()) {
                    val g = generacionUsuario.toIntOrNull()
                    if (g == null || g !in 1..9) {
                        _errorMensaje.value = "La generación debe ser un número entre 1 y 9"
                        return@launch
                    }
                    numeroGeneracionFiltro = g
                }

                val porNombreYRango = listaOriginal.filter { pokemon ->
                    val idActual = extraerId(pokemon.url)
                    val cumpleNombre = pokemon.name.contains(nombre, ignoreCase = true)
                    val cumpleRango = rangoIds == null || idActual in rangoIds
                    cumpleNombre && cumpleRango
                }

                val resultadoFinal = if (tipoNormalizado.isEmpty() && numeroGeneracionFiltro == null) {
                    porNombreYRango
                } else {
                    val listaFiltrada = mutableListOf<PokemonResult>()
                    for (pokemon in porNombreYRango) {
                        if (cumpleTipoYGeneracion(pokemon, tipoNormalizado, numeroGeneracionFiltro)) {
                            listaFiltrada.add(pokemon)
                        }
                    }
                    listaFiltrada
                }

                _pokemonList.value = resultadoFinal
            } finally {
                _filtrando.value = false
            }
        }
    }

    private suspend fun cumpleTipoYGeneracion(
        pokemon: PokemonResult,
        tipoNormalizado: String,
        numeroGeneracionFiltro: Int?
    ): Boolean {
        val cumpleTipo = if (tipoNormalizado.isEmpty()) {
            true
        } else {
            try {
                val detalles = repository.getPokemonDetails(pokemon.name)
                detalles.types.any { it.type.name.lowercase() == tipoNormalizado }
            } catch (_: Exception) {
                false
            }
        }

        val cumpleGeneracion = if (numeroGeneracionFiltro == null) {
            true
        } else {
            try {
                val especie = repository.obtenerEspecie(pokemon.name)
                val numeroApi = numeroGeneracionDesdeNombreApi(especie.generation.name)
                numeroApi == numeroGeneracionFiltro
            } catch (_: Exception) {
                false
            }
        }

        return cumpleTipo && cumpleGeneracion
    }

    private fun numeroGeneracionDesdeNombreApi(nombreApi: String): Int? {
        val sufijo = nombreApi.removePrefix("generation-")
        return when (sufijo) {
            "i" -> 1
            "ii" -> 2
            "iii" -> 3
            "iv" -> 4
            "v" -> 5
            "vi" -> 6
            "vii" -> 7
            "viii" -> 8
            "ix" -> 9
            else -> null
        }
    }

    private fun extraerId(url: String): Int {
        return url.split("/").filter { it.isNotEmpty() }.last().toIntOrNull() ?: 0
    }

    companion object {
        // Nombres de tipo exactamente como los usa la API (en inglés, minúsculas)
        private val TIPOS_VALIDOS = setOf(
            "normal", "fire", "water", "electric", "grass", "ice",
            "fighting", "poison", "ground", "flying", "psychic", "bug",
            "rock", "ghost", "dragon", "dark", "steel", "fairy"
        )
    }
}
