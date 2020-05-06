package io.github.mattpvaughn.pokeapp.injection.components

import android.content.Context
import dagger.Component
import io.github.mattpvaughn.pokeapp.application.CustomApplication
import io.github.mattpvaughn.pokeapp.data.local.*
import io.github.mattpvaughn.pokeapp.data.remote.*
import io.github.mattpvaughn.pokeapp.features.details.DetailsFragment
import io.github.mattpvaughn.pokeapp.features.library.LibraryViewModelFactory
import io.github.mattpvaughn.pokeapp.injection.modules.AppModule
import retrofit2.Retrofit
import java.io.File
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun applicationContext(): Context
    fun internalFilesDir(): File
    fun externalDeviceDirs(): List<File>
    fun modelDao(): ModelDao
    fun prefsRepo(): PrefsRepo
    fun modelRepo(): ModelRepository
    fun retrofit(): Retrofit
    fun libraryViewModelFactory(): LibraryViewModelFactory
    fun pokemonService(): PokemonAPIService

    // Inject
    fun inject(customApplication: CustomApplication)
    fun inject(detailsFragment: DetailsFragment)
}