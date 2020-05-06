package io.github.mattpvaughn.pokeapp.data.remote.model

import com.squareup.moshi.JsonClass
import io.github.mattpvaughn.pokeapp.data.local.model.Pokemon

@JsonClass(generateAdapter = true)
data class PokemonPageResults(
    val count : Long = 0,
    val next: String = "",
    val results: List<PokemonResults> = emptyList()
)

fun PokemonPageResults?.toPokemonList(): List<Pokemon> {
    return this?.results?.map { it.toPokemon() }?: emptyList()
}
