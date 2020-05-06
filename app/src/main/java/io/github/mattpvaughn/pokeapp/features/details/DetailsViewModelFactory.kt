package io.github.mattpvaughn.pokeapp.features.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.mattpvaughn.pokeapp.data.remote.PokemonAPIService

class DetailsViewModelFactory(
    private val pokemonName: String,
    private val pokemonAPIService: PokemonAPIService
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            DetailsViewModel(pokemonName, pokemonAPIService) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }


}
