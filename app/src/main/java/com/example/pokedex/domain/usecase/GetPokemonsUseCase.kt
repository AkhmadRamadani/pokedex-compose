package com.example.pokedex.domain.usecase

import com.example.pokedex.common.Result
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPokemonsUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    operator fun invoke(query: String = ""): Flow<Result<List<Pokemon>>> {
        return if (query.isBlank()) {
            repository.getPokemons()
        } else {
            repository.getPokemons().let { flow ->
                kotlinx.coroutines.flow.flow {
                    flow.collect { result ->
                        emit(result.filterByName(query))
                    }
                }
            }
        }
    }

    private fun Result<List<Pokemon>>.filterByName(query: String): Result<List<Pokemon>> =
        when (this) {
            is Result.Success -> Result.Success(
                data.filter { it.name.contains(query, ignoreCase = true) }
            )
            else -> this
        }
}
