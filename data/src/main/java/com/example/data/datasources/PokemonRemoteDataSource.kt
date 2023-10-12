package com.example.data.datasources

import com.example.data.apiservices.PokemonApiService
import com.example.data.models.BaseResponse
import com.example.data.models.Pokemon

class PokemonRemoteDataSource(
    private val api: PokemonApiService
) {

    suspend fun getPokemonList(offset: Int, limit: Int): BaseResponse<List<Pokemon>> = api.getPokemonList(offset, limit)

}