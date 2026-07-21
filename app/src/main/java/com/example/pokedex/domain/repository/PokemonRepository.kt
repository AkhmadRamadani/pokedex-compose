package com.example.pokedex.domain.repository

import com.example.pokedex.common.Result
import com.example.pokedex.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPokemons(): Flow<Result<List<Pokemon>>>

    fun getPokemonById(id: Int): Flow<Result<Pokemon>>
}
