package com.example.pokemonsapp.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonsapp.databinding.ActivityMainBinding
import com.example.pokemonsapp.ui.main.MainViewModel.UiModel
import com.example.pokemonsapp.ui.pokemonDetailed.PokemonDetailedActivity
import com.example.pokemonsapp.utils.extensions.showErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val mAdapter: PokemonAdapter by lazy {
        PokemonAdapter(onItemClicked = { pokemonId ->
            navigateToPokemonDetails(pokemonId)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) { // According to Google Documentation
                    viewModel.model.observe(this@MainActivity, ::updateUi)
                }
            }
            rvPokemons.adapter = mAdapter
            rvPokemons.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        viewModel.requestPokemonList()
                    }
                }
            })
            viewModel.requestPokemonList()
        }
    }

    private fun updateUi(model: UiModel) {
        binding.pbLoading.visibility = View.GONE
        when (model) {
            is UiModel.Loading -> binding.pbLoading.visibility = View.VISIBLE
            is UiModel.Error -> showErrorMessage(model.errorEntity)
            is UiModel.LoadPokemonList -> mAdapter.pokemons =
                (mAdapter.pokemons + model.pokemons).toMutableList()
        }
    }

    //region navigation

    private fun navigateToPokemonDetails(pokemonId: Int) {
        val intent = PokemonDetailedActivity.getStartIntent(this, pokemonId)
        startActivity(intent)
    }

    //endregion navigation

}