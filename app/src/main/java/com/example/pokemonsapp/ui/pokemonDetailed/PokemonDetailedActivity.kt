package com.example.pokemonsapp.ui.pokemonDetailed

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemonsapp.databinding.ActivityPokemonDetailedBinding

class PokemonDetailedActivity : AppCompatActivity() {

    companion object {
        private const val POKEMON_ID = "POKEMON_ID"
        fun getStartIntent(mContext: Context, pokemonId: Int): Intent {
            return Intent(mContext, PokemonDetailedActivity::class.java).apply {
                putExtra(POKEMON_ID, pokemonId)
            }
        }
    }

    private lateinit var binding: ActivityPokemonDetailedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}