package com.example.domain.repositories

import com.example.domain.models.ErrorEntity
import com.example.domain.models.Pokemon
import com.example.domain.models.ResultWrapper

interface PokemonRepository {

    suspend fun getPokemonList(page: Int): ResultWrapper<ErrorEntity, List<Pokemon>>

}