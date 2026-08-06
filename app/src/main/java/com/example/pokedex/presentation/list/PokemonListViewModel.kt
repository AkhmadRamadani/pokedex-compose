package com.example.pokedex.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.common.Result
import com.example.pokedex.domain.usecase.GetPokemonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val getPokemonsUseCase: GetPokemonsUseCase
) : ViewModel() {

    private val searchQuery = MutableStateFlow("")

    private val retrySignal = MutableStateFlow(0)

    private val debouncedQuery = searchQuery
        .debounce(250)
        .distinctUntilChanged()

    private val listResult = combine(debouncedQuery, retrySignal) { query, _ -> query }
        .flatMapLatest { query -> getPokemonsUseCase(query) }

    val uiState: StateFlow<PokemonListUiState> = combine(
        listResult,
        searchQuery
    ) { result, query ->
        when (result) {
            is Result.Loading -> PokemonListUiState(isLoading = true, query = query)
            is Result.Success -> PokemonListUiState(pokemons = result.data, query = query)
            is Result.Error -> PokemonListUiState(
                errorMessage = result.message ?: "Unknown error",
                query = query
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PokemonListUiState(isLoading = true)
    )

    fun onQueryChanged(query: String) {
        searchQuery.value = query
    }

    fun retry() {
        retrySignal.value += 1
    }
}
