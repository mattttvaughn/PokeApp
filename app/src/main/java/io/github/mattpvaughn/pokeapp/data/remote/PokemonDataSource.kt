package io.github.mattpvaughn.pokeapp.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.github.mattpvaughn.pokeapp.data.local.model.Pokemon
import io.github.mattpvaughn.pokeapp.data.remote.model.toPokemonList
import io.github.mattpvaughn.pokeapp.util.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PokemonDataSource(
    private val pokemonAPIService: PokemonAPIService, private val lifecycleScope: CoroutineScope
) : PageKeyedDataSource<Long, Pokemon>() {

    private var _networkState = MutableLiveData<NetworkState>(NetworkState.INITIALIZED)
    val networkState: LiveData<NetworkState>
        get() = _networkState

    /*
    * Loads the data when app is launched for the first time. We fetch the first page of
    * data from the api and pass it via the callback method to the UI.
    */
    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<Long, Pokemon?>) {
        _networkState.postValue(NetworkState.LOADING)
        lifecycleScope.launch {
            try {
                val pokemonPage = pokemonAPIService.fetchPokemonPage(0L, params.requestedLoadSize)
                callback.onResult(pokemonPage.toPokemonList(), null, params.requestedLoadSize.toLong())
                _networkState.postValue(NetworkState.LOADED)
            } catch (e: Exception) {
                _networkState.postValue(NetworkState.ERROR(e.message!!))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Long, Pokemon?>) {}

    /* Loads the data for all subsequent requests for further pages */
    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Long, Pokemon?>) {
        _networkState.postValue(NetworkState.LOADING)
        lifecycleScope.launch {
            try {
                val pokemonPage = pokemonAPIService.fetchPokemonPage(
                    params.key, params.requestedLoadSize
                )
                val nextKey = params.key + PAGE_SIZE
                callback.onResult(pokemonPage.toPokemonList(), nextKey)
                _networkState.postValue(NetworkState.LOADED)
            } catch (e: Exception) {
                _networkState.postValue(NetworkState.ERROR(e.message!!))
            }
        }
    }
}