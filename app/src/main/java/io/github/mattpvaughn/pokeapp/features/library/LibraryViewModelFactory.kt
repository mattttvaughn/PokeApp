package io.github.mattpvaughn.pokeapp.features.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.mattpvaughn.pokeapp.data.remote.PokemonAPIService
import javax.inject.Inject

class LibraryViewModelFactory @Inject constructor(private val pokemonAPIService: PokemonAPIService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LibraryViewModel::class.java!!)) {
            LibraryViewModel(pokemonAPIService) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
