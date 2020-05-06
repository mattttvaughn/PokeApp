package io.github.mattpvaughn.pokeapp.injection.modules

import dagger.Module
import dagger.Provides
import io.github.mattpvaughn.pokeapp.application.MainActivity
import io.github.mattpvaughn.pokeapp.injection.scopes.ActivityScope
import io.github.mattpvaughn.pokeapp.navigation.Navigator

@Module
class ActivityModule(private val activity: MainActivity) {

    @Provides
    @ActivityScope
    fun navigator(): Navigator = Navigator(activity.supportFragmentManager)

}


