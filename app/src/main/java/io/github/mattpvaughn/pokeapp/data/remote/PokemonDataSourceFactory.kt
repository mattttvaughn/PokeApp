package io.github.mattpvaughn.pokeapp.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.DataSource.Factory
import io.github.mattpvaughn.pokeapp.data.local.model.Pokemon
import kotlinx.coroutines.CoroutineScope


class PokemonDataSourceFactory(
    private val pokemonAPIService: PokemonAPIService,
    private val lifecycleScope: CoroutineScope
) : Factory<Long, Pokemon>() {

    private val _pokemonLiveData = MutableLiveData<PokemonDataSource?>()
    val pokemonLiveData : LiveData<PokemonDataSource?>
        get() = _pokemonLiveData

    private var pokemonDataSource: PokemonDataSource? = null

    override fun create(): DataSource<Long, Pokemon>? {
        pokemonDataSource = PokemonDataSource(pokemonAPIService, lifecycleScope)
        _pokemonLiveData.postValue(pokemonDataSource)
        return pokemonDataSource
    }
}