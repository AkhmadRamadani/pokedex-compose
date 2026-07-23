package com.example.pokedex.data.remote

import com.example.pokedex.BuildConfig
import com.example.pokedex.data.remote.dto.PokemonDto
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonApi {

    @GET
    suspend fun getPokemons(@Url url: String = BuildConfig.POKEMON_JSON_URL): List<PokemonDto>
}

