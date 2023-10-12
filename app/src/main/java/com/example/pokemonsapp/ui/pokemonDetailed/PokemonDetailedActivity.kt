package com.example.pokemonsapp.ui.pokemonDetailed

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.domain.models.PokemonDetail
import com.example.domain.models.Types
import com.example.pokemonsapp.databinding.ActivityPokemonDetailedBinding
import com.example.pokemonsapp.ui.pokemonDetailed.PokemonDetailedViewModel.UiModel
import com.example.pokemonsapp.utils.constants.NOT_VALUE
import com.example.pokemonsapp.utils.extensions.loadImage
import com.example.pokemonsapp.utils.extensions.showErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailedActivity : AppCompatActivity() {

    companion object {
        private const val POKEMON_ID = "POKEMON_ID"
        fun getStartIntent(mContext: Context, pokemonId: Int): Intent {
            val intent = Intent(mContext, PokemonDetailedActivity::class.java)
            intent.putExtra(POKEMON_ID, pokemonId)
            return intent
        }
    }

    private lateinit var binding: ActivityPokemonDetailedBinding
    private val viewModel: PokemonDetailedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPokemonDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.model.observe(this@PokemonDetailedActivity, ::updateUi)
                }
            }

            val pokemonId = intent.extras?.getInt(POKEMON_ID) ?: NOT_VALUE
            btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
            viewModel.requestPokemonDetail(pokemonId)
        }
    }

    private fun updateUi(model: UiModel) {
        binding.pbLoading.visibility = View.GONE
        when (model) {
            is UiModel.Loading -> binding.pbLoading.visibility = View.VISIBLE
            is UiModel.Error -> showErrorMessage(model.errorEntity)
            is UiModel.LoadPokemonDetail -> loadPokemonInfo(model.pokemon)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadPokemonInfo(pokemon: PokemonDetail) {
        binding.apply {
            imgPokemonSprite.loadImage(pokemon.sprites.frontDefault.toString())
            tvName.text = pokemon.name
            tvHeight.text = "Altura: ${pokemon.height} m"
            tvWeight.text = "Pesos: ${pokemon.weight} kg"
            tvPokemonTypes.text = "Tipos: " + getAllTypes(pokemon.types)
        }
    }

    private fun getAllTypes(types: List<Types>): String {
        var typesStr = ""
        types.map {
            typesStr += if (typesStr.isEmpty()) {
                it.type.name
            } else {
                ", ${it.type.name}"
            }
        }
        return typesStr
    }

}