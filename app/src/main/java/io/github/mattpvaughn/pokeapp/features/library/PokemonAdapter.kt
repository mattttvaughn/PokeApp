package io.github.mattpvaughn.pokeapp.features.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.github.mattpvaughn.pokeapp.data.local.model.Pokemon
import io.github.mattpvaughn.pokeapp.databinding.PokemonListItemBinding
import io.github.mattpvaughn.pokeapp.databinding.ProgressListItemBinding
import io.github.mattpvaughn.pokeapp.util.NetworkState
import java.lang.IllegalStateException


class PokemonAdapter(private val modelClick: LibraryFragment.ModelClick) :
    PagedListAdapter<Pokemon, RecyclerView.ViewHolder>(PokemonDiffCallback()) {

    private var networkState: NetworkState = NetworkState.INITIALIZED

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_PROGRESS) {
            val headerBinding: ProgressListItemBinding =
                ProgressListItemBinding.inflate(layoutInflater, parent, false)
            ProgressViewHolder(headerBinding)
        } else {
            val itemBinding: PokemonListItemBinding =
                PokemonListItemBinding.inflate(layoutInflater, parent, false)
            PokemonViewHolder(itemBinding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PokemonViewHolder) {
            getItem(position)?.let { holder.bind(it, modelClick) }
        } else {
            (holder as ProgressViewHolder).bind(networkState)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            TYPE_PROGRESS
        } else {
            TYPE_ITEM
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState !== NetworkState.LOADED
    }

    fun setNetworkState(newNetworkState: NetworkState) {
        val previousState = networkState
        val previousExtraRow = hasExtraRow()
        networkState = newNetworkState
        val newExtraRow = hasExtraRow()
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(itemCount)
            } else {
                notifyItemInserted(itemCount)
            }
        } else if (newExtraRow && previousState !== newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    class PokemonViewHolder constructor(private val pokemonItemBinding: PokemonListItemBinding) :
        RecyclerView.ViewHolder(pokemonItemBinding.root) {

        fun bind(pokemon: Pokemon, modelClick: LibraryFragment.ModelClick) {
            pokemonItemBinding.bind(pokemon)
            pokemonItemBinding.root.setOnClickListener { modelClick.onClick(pokemon) }
        }

        companion object {
            fun from(viewGroup: ViewGroup): PokemonViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = PokemonListItemBinding.inflate(inflater, viewGroup, false)
                return PokemonViewHolder(binding)
            }
        }
    }

    class ProgressViewHolder constructor(private val binding: ProgressListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(networkState: NetworkState) {
            when (networkState) {
                is NetworkState.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is NetworkState.LOADED -> {
                    binding.progress.visibility = View.GONE
                }
                is NetworkState.ERROR -> {
                    binding.progress.visibility = View.GONE
                    binding.message.text = networkState.message
                }
                is NetworkState.INITIALIZED -> throw IllegalStateException(
                    "Cannot show an item if network has not been initialized"
                )
            }
        }

        companion object {
            fun from(viewGroup: ViewGroup): ProgressViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = ProgressListItemBinding.inflate(inflater, viewGroup, false)
                return ProgressViewHolder(binding)
            }
        }
    }


    companion object {
        private const val TYPE_PROGRESS = 0
        private const val TYPE_ITEM = 1
    }

}

class PokemonDiffCallback : DiffUtil.ItemCallback<Pokemon>() {
    override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
        return oldItem.id == newItem.id && oldItem.name == newItem.name && oldItem.thumb == newItem.thumb
    }
}
