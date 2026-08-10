package com.example.pokedex.domain.usecase

import com.example.pokedex.common.Result
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakePokemonRepository : PokemonRepository {

    var pokemonsToReturn: List<Pokemon> = sampleData()
    var errorToReturn: Throwable? = null

    override fun getPokemons(): Flow<Result<List<Pokemon>>> = flow {
        emit(Result.Loading)
        val error = errorToReturn
        if (error != null) {
            emit(Result.Error(error, error.message))
        } else {
            emit(Result.Success(pokemonsToReturn))
        }
    }

    override fun getPokemonById(id: Int): Flow<Result<Pokemon>> = flow {
        emit(Result.Loading)
        val pokemon = pokemonsToReturn.firstOrNull { it.id == id }
        if (pokemon != null) {
            emit(Result.Success(pokemon))
        } else {
            emit(Result.Error(NoSuchElementException("not found")))
        }
    }

    companion object {
        fun sampleData(): List<Pokemon> {
            val ivysaur = Pokemon(2, "#002", "Ivysaur", null, null, null, listOf("Grass", "Poison"), "0.99 m", "13.0 kg", listOf("Fire"), reason = "(Level 16)")
            val bulbasaur = Pokemon(1, "#001", "Bulbasaur", null, null, null, listOf("Grass", "Poison"), "0.71 m", "6.9 kg", listOf("Fire"), evolutions = listOf(ivysaur))
            val charmander = Pokemon(4, "#004", "Charmander", null, null, null, listOf("Fire"), "0.61 m", "8.5 kg", listOf("Water"))
            val squirtle = Pokemon(7, "#007", "Squirtle", null, null, null, listOf("Water"), "0.51 m", "9.0 kg", listOf("Electric", "Grass"))
            return listOf(bulbasaur, charmander, squirtle)
        }
    }
}
