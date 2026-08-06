package com.example.pokedex.presentation.list

import com.example.pokedex.domain.model.Pokemon

data class PokemonListUiState(
    val isLoading: Boolean = false,
    val pokemons: List<Pokemon> = emptyList(),
    val query: String = "",
    val errorMessage: String? = null
) {
    val isEmpty: Boolean get() = !isLoading && pokemons.isEmpty() && errorMessage == null
}
