package io.github.mattpvaughn.pokeapp.features.details

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.github.mattpvaughn.pokeapp.application.APP_NAME
import io.github.mattpvaughn.pokeapp.application.CustomApplication
import io.github.mattpvaughn.pokeapp.data.remote.PokemonAPIService
import io.github.mattpvaughn.pokeapp.databinding.FragmentDetailsBinding
import io.github.mattpvaughn.pokeapp.util.observeEvent
import javax.inject.Inject

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()
        const val ARG_POKEMON_NAME = "pokename"
    }

    @Inject
    lateinit var pokemonAPIService: PokemonAPIService

    override fun onAttach(context: Context) {
        // !! is okay here because we're guaranteed non-null activity
        (activity!!.application as CustomApplication).appComponent.inject(this)
        Log.i(APP_NAME, "DetailsFragment onAttach()")
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Log.i(APP_NAME, "DetailsFragment onCreateView()")

        // Activity and context are always non-null on view creation. This informs lint about that
        val context = context!!

        val binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val pokemonName = requireNotNull(arguments).getString(ARG_POKEMON_NAME, "")

        val factory = DetailsViewModelFactory(pokemonName, pokemonAPIService)
        val viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)

        viewModel.messageForUser.observeEvent(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, LENGTH_SHORT).show()
        }

        viewModel.isLoadingDetails.observe(viewLifecycleOwner, Observer { isLoading ->
            binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.pokemonLiveData.observe(viewLifecycleOwner, Observer { pokemon ->
            if (pokemon != null) {
                binding.bind(pokemon)
            }
        })

        binding.detailsToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        return binding.root
    }
}
