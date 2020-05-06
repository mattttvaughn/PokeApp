package io.github.mattpvaughn.pokeapp.features.details

import androidx.lifecycle.*
import io.github.mattpvaughn.pokeapp.data.local.model.Pokemon
import io.github.mattpvaughn.pokeapp.data.remote.PokemonAPIService
import io.github.mattpvaughn.pokeapp.data.remote.model.toPokemon
import io.github.mattpvaughn.pokeapp.util.Event
import io.github.mattpvaughn.pokeapp.util.postEvent
import kotlinx.coroutines.launch

class DetailsViewModel(pokemonName: String, pokemonAPIService: PokemonAPIService) : ViewModel() {

    private var _messageForUser = MutableLiveData<Event<String>>()
    val messageForUser: LiveData<Event<String>>
        get() = _messageForUser

    private var _isLoadingDetails = MutableLiveData(true)
    val isLoadingDetails: LiveData<Boolean>
        get() = _isLoadingDetails

    private var _pokemonLiveData= MutableLiveData<Pokemon?>(null)
    val pokemonLiveData: LiveData<Pokemon?>
        get() = _pokemonLiveData

    init {
        viewModelScope.launch {
            _isLoadingDetails.postValue(true)
            try {
                val pokemon = pokemonAPIService.fetchPokemon(pokemonName).toPokemon()
                _pokemonLiveData.postValue(pokemon)
                _isLoadingDetails.postValue(false)
            } catch (e: Exception) {
    showUserMessage("Error loading pokemon: ${e.message}")
            }
        }
    }

    private fun showUserMessage(message: String) {
        _messageForUser.postEvent(message)
    }

}
