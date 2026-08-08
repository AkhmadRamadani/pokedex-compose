package com.example.pokedex.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.common.Result
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.usecase.GetPokemonDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PokemonDetailUiState(
    val isLoading: Boolean = true,
    val pokemon: Pokemon? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonId: Int = checkNotNull(savedStateHandle["pokemonId"])

    private val _uiState = MutableStateFlow(PokemonDetailUiState())
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getPokemonDetailUseCase(pokemonId).collect { result ->
                _uiState.value = when (result) {
                    is Result.Loading -> _uiState.value.copy(isLoading = true, errorMessage = null)
                    is Result.Success -> PokemonDetailUiState(isLoading = false, pokemon = result.data)
                    is Result.Error -> PokemonDetailUiState(
                        isLoading = false,
                        errorMessage = result.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}
