package io.github.mattpvaughn.pokeapp.features.library

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.github.mattpvaughn.pokeapp.application.MainActivity
import io.github.mattpvaughn.pokeapp.data.local.PrefsRepo
import io.github.mattpvaughn.pokeapp.data.local.model.Pokemon
import io.github.mattpvaughn.pokeapp.databinding.FragmentLibraryBinding
import io.github.mattpvaughn.pokeapp.navigation.Navigator
import javax.inject.Inject

class LibraryFragment : Fragment() {

    companion object {
        fun newInstance() = LibraryFragment()
    }

    @Inject
    lateinit var prefsRepo: PrefsRepo

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: LibraryViewModelFactory

    private val adapter = PokemonAdapter(object : ModelClick {
        override fun onClick(pokemon: Pokemon) {
            openModelDetails(pokemon)
        }
    })

    override fun onAttach(context: Context) {
        (activity as MainActivity).activityComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLibraryBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this, viewModelFactory).get(LibraryViewModel::class.java)

        binding.libraryList.adapter = adapter

        viewModel.pokemonLiveData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.networkState.observe(viewLifecycleOwner, Observer {
            adapter.setNetworkState(it)
        })

        (activity as MainActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun openModelDetails(pokemon: Pokemon) {
        navigator.openDetails(pokemon.name)
    }

    interface ModelClick {
        fun onClick(pokemon: Pokemon)
    }
}
