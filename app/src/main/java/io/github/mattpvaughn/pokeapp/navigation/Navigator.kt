package io.github.mattpvaughn.pokeapp.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import io.github.mattpvaughn.pokeapp.R
import io.github.mattpvaughn.pokeapp.features.details.DetailsFragment
import io.github.mattpvaughn.pokeapp.features.details.DetailsFragment.Companion.ARG_POKEMON_NAME
import io.github.mattpvaughn.pokeapp.features.library.LibraryFragment

class Navigator(private val fragmentManager: FragmentManager) {

    fun openLibrary() {
        val libraryFragment = LibraryFragment.newInstance()

        fragmentManager.beginTransaction().replace(R.id.fragNavHost, libraryFragment)
            .addToBackStack(null).commit()
    }

    fun openDetails(pokemonName: String) {
        val detailsFragment = DetailsFragment.newInstance()
        detailsFragment.arguments = Bundle().apply {
            putString(ARG_POKEMON_NAME, pokemonName)
        }

        fragmentManager.beginTransaction().replace(R.id.fragNavHost, detailsFragment)
            .addToBackStack(null).commit()

    }
}