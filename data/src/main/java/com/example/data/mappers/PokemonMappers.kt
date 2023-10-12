package com.example.data.mappers

import com.example.data.BuildConfig
import com.example.data.utils.getPokemonId
import com.example.domain.models.Pokemon as DomainPokemon
import com.example.data.models.Pokemon as ServerPokemon

fun List<ServerPokemon>.toDomainPokemonList(): List<DomainPokemon> {
    return this.map {
        DomainPokemon(
            id = it.url?.getPokemonId()?.toInt(),
            name = it.name,
            url = it.url,
            image = BuildConfig.API_MEDIA_URL + it.url?.getPokemonId() + ".png"
        )
    }
}
