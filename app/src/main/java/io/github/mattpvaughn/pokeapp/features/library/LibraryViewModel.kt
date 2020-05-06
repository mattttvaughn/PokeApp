package io.github.mattpvaughn.pokeapp.features.library

import androidx.lifecycle.*
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.github.mattpvaughn.pokeapp.data.remote.PAGE_SIZE
import io.github.mattpvaughn.pokeapp.data.remote.PokemonAPIService
import io.github.mattpvaughn.pokeapp.data.remote.PokemonDataSource
import io.github.mattpvaughn.pokeapp.data.remote.PokemonDataSourceFactory
import io.github.mattpvaughn.pokeapp.util.NetworkState


class LibraryViewModel(pokemonAPIService: PokemonAPIService) : ViewModel() {

    private val pokemonDataSourceFactory = PokemonDataSourceFactory(pokemonAPIService, viewModelScope)

    val networkState: LiveData<NetworkState> =
        Transformations.switchMap(pokemonDataSourceFactory.pokemonLiveData) { dataSource: PokemonDataSource? ->
            dataSource?.networkState
        }

    private val pagedListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(PAGE_SIZE).build()

    val pokemonLiveData = LivePagedListBuilder(pokemonDataSourceFactory, pagedListConfig).build()

}
