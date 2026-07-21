package com.example.pokedex.domain.usecase

import com.example.pokedex.common.Result
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(id: Int): Flow<Result<Pokemon>> = repository.getPokemonById(id)
}
