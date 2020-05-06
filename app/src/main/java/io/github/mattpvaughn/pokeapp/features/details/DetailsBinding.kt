package io.github.mattpvaughn.pokeapp.features.details

import com.bumptech.glide.Glide
import io.github.mattpvaughn.pokeapp.data.local.model.Pokemon
import io.github.mattpvaughn.pokeapp.databinding.FragmentDetailsBinding

fun FragmentDetailsBinding.bind(pokemon: Pokemon) {
    this.name.text = pokemon.name
    this.type.text = pokemon.type
    this.weight.text = "${pokemon.weight} kg"
    this.detailsArtwork.contentDescription = "Image of ${pokemon.name}"

    Glide.with(this.detailsArtwork)
        .load(pokemon.thumb)
        .into(this.detailsArtwork)
}

