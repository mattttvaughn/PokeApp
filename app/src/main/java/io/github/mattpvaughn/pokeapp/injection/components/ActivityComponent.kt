package io.github.mattpvaughn.pokeapp.injection.components

import dagger.Component
import io.github.mattpvaughn.pokeapp.application.MainActivity
import io.github.mattpvaughn.pokeapp.application.MainActivityViewModelFactory
import io.github.mattpvaughn.pokeapp.features.library.LibraryFragment
import io.github.mattpvaughn.pokeapp.injection.modules.ActivityModule
import io.github.mattpvaughn.pokeapp.injection.scopes.ActivityScope
import io.github.mattpvaughn.pokeapp.navigation.Navigator

@ActivityScope
@Component(dependencies = [AppComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun mainActivityViewModelFactory(): MainActivityViewModelFactory
    fun navigator(): Navigator

    fun inject(activity: MainActivity)
    fun inject(libraryFragment: LibraryFragment)
}

