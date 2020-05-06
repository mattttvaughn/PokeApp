package io.github.mattpvaughn.pokeapp.application

import io.github.mattpvaughn.pokeapp.injection.components.AppComponent

class Injector private constructor() {
    companion object {
        fun get() : AppComponent = CustomApplication.get().appComponent
    }
}