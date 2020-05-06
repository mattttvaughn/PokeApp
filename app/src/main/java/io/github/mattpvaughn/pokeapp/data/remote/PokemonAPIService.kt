package io.github.mattpvaughn.pokeapp.data.remote

import io.github.mattpvaughn.pokeapp.data.remote.model.PokemonPageResults
import io.github.mattpvaughn.pokeapp.data.remote.model.PokemonResults
import retrofit2.http.*


const val BASE_URL = "https://pokeapi.co/"

// Number of items to load per page
const val PAGE_SIZE = 20

// The offset in the API is not per page, but per item, so every page loaded in at the
// `DataSource` has to be called with a parameter of `PAGE_NUMBER * PAGE_SIZE`
const val OFFSET_MULTIPLIER = PAGE_SIZE

interface PokemonAPIService {
    /** A basic call */
    @GET("/api/v2/pokemon")
    suspend fun fetchPokemonPage(
        @Query("offset") offset: Long,
        @Query("limit") limit: Int
    ): PokemonPageResults

    @GET("/api/v2/pokemon/{name}")
    suspend fun fetchPokemon(@Path("name") name: String): PokemonResults

}

