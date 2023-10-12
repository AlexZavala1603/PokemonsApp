package com.example.pokemonsapp.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.Pokemon
import com.example.pokemonsapp.R
import com.example.pokemonsapp.databinding.ItemPokemonBinding
import com.example.pokemonsapp.ui.main.PokemonAdapter.PokemonHolder
import com.example.pokemonsapp.utils.constants.NOT_VALUE
import kotlin.properties.Delegates

class PokemonAdapter(
    private val onItemClicked: (pokemonId: Int) -> Unit
) : RecyclerView.Adapter<PokemonHolder>() {

    var pokemons: MutableList<Pokemon> by Delegates.observable(mutableListOf()) { _, old, new ->
        DiffUtil.calculateDiff(PokemonDiffUtil(old, new), false).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_pokemon, parent, false)
        return PokemonHolder(view)
    }

    override fun getItemCount(): Int = pokemons.size

    override fun onBindViewHolder(holder: PokemonHolder, position: Int) {
        val item = pokemons[position]
        holder.bind(item)
    }

    inner class PokemonHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPokemonBinding.bind(itemView)

        fun bind(item: Pokemon) {
            binding.apply {
                val animation =
                    AnimationUtils.loadAnimation(itemView.context, R.anim.slide_in_bottom)
                itemView.startAnimation(animation)

                itemContainer.setOnClickListener { onItemClicked.invoke(item.id ?: NOT_VALUE) }

                tvPokemonName.text = item.name
                item.image?.let {
                    imgPokemon.setImageInformation(
                        item.image.toString(),
                        item.name.toString(),
                        ContextCompat.getDrawable(itemView.context, R.drawable.img_placeholder)
                    )
                }

            }
        }
    }

}