package io.github.mattpvaughn.pokeapp.data.remote.model

import android.util.Log
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.github.mattpvaughn.pokeapp.data.local.model.Pokemon

@JsonClass(generateAdapter = true)
data class PokemonResults(
    val name: String = "",
    val id: Int = 0,
    val url: String = "",
    val types: List<PokemonTypeWrapper> = emptyList(),
    val weight: Int = 0,
    val sprites: Sprite = Sprite()
)

@JsonClass(generateAdapter = true)
data class Sprite (@Json(name = "front_default") val frontSprite: String = "")

@JsonClass(generateAdapter = true)
data class PokemonTypeWrapper (val type: PokemonType = PokemonType()) {
    override fun toString(): String {
        return type.name
    }
}

@JsonClass(generateAdapter = true)
data class PokemonType (val name: String = "")

fun PokemonResults.toPokemon(): Pokemon {
    val pokeId = if (id == 0) {
        val split = url.split("/")
        split[split.size - 2].toInt() ?: 0
    } else {
        id
    }
    Log.i("TAG", "Id is $pokeId")
    return Pokemon(id = pokeId,
        name = name,
        type = types.joinToString(),
        weight = weight,
        thumb = sprites.frontSprite)
}
