package io.github.mattpvaughn.pokeapp.application

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import io.github.mattpvaughn.pokeapp.data.local.ModelRepository
import javax.inject.Inject

class MainActivityViewModelFactory @Inject constructor(
    private val modelRepository: ModelRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(modelRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewHolder class")
    }
}