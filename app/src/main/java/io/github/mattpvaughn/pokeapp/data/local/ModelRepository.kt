package io.github.mattpvaughn.pokeapp.data.local

import androidx.lifecycle.LiveData
import io.github.mattpvaughn.pokeapp.data.local.model.Pokemon
import io.github.mattpvaughn.pokeapp.data.remote.PokemonAPIService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ModelRepository @Inject constructor(
    private val modelDao: ModelDao,
    private val pokemonAPIService: PokemonAPIService
) {

     fun getAllModels(): LiveData<List<Pokemon>> {
        return modelDao.getAllRows()
    }

     suspend fun clear() {
        withContext(Dispatchers.IO) {
            modelDao.clear()
        }
    }

     fun getModel(id: Int): LiveData<Pokemon> {
        return modelDao.getModel(id)
    }

    fun search(query: String): LiveData<List<Pokemon>> {
        return modelDao.search(query)
    }

}