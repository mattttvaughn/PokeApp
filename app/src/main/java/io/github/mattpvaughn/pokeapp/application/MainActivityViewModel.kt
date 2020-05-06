package io.github.mattpvaughn.pokeapp.application

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.github.mattpvaughn.pokeapp.data.local.ModelRepository
import io.github.mattpvaughn.pokeapp.util.Event
import io.github.mattpvaughn.pokeapp.util.postEvent
import javax.inject.Inject


class MainActivityViewModel @Inject constructor(
    private val modelRepository: ModelRepository
) : ViewModel() {

    private var _errorMessage = MutableLiveData<Event<String>>()
    val errorMessage: LiveData<Event<String>>
        get() = _errorMessage

    fun showUserMessage(errorMessage: String) {
        _errorMessage.postEvent(errorMessage)
    }
}

