package io.github.mattpvaughn.pokeapp.features.library

import android.graphics.Color
import com.bumptech.glide.Glide
import io.github.mattpvaughn.pokeapp.R
import io.github.mattpvaughn.pokeapp.data.local.model.Pokemon
import io.github.mattpvaughn.pokeapp.databinding.PokemonListItemBinding

fun PokemonListItemBinding.bind(pokemon: Pokemon) {
    title.text = pokemon.name.capitalize()
    thumb.contentDescription = "Image of ${pokemon.name}"

    try {
        // Cached icons locally for the list to be a conscientious API user
        val name = "a${pokemon.id}"
        android.util.Log.i("TAG", "Looking for $name")
        val resourceId = root.resources.getIdentifier(
            name, "drawable", root.context.packageName
        )
        Glide.with(root).load(resourceId).placeholder(R.drawable.a0).into(thumb)
    } catch (e: Exception) {
        // do nada
    }

    card.setCardBackgroundColor(funColors[pokemon.id % 5])
}

val funColors = listOf(
    Color.argb(0xFF, 0xD1, 0xF2, 0xA5),
    Color.argb(0xFF, 0xF5, 0x91, 0xA6),
    Color.argb(0xFF, 0xFF, 0x9F, 0x80),
    Color.argb(0xFF, 0xFF, 0xC4, 0x8C),
    Color.argb(0xFF, 0xEF, 0xFA, 0xB4)
)