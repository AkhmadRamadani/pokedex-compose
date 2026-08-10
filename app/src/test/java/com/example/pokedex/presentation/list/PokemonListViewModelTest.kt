package com.example.pokedex.presentation.list

import app.cash.turbine.test
import com.example.pokedex.MainDispatcherRule
import com.example.pokedex.domain.usecase.FakePokemonRepository
import com.example.pokedex.domain.usecase.GetPokemonsUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class PokemonListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun buildViewModel(repository: FakePokemonRepository = FakePokemonRepository()) =
        PokemonListViewModel(GetPokemonsUseCase(repository)) to repository

    @Test
    fun `initial state is loading then resolves to success with all pokemons`() = runTest {
        val (viewModel, _) = buildViewModel()

        viewModel.uiState.test {
            assertThat(awaitItem().isLoading).isTrue()
            val loaded = awaitItem()
            assertThat(loaded.isLoading).isFalse()
            assertThat(loaded.pokemons).hasSize(3)
        }
    }

    @Test
    fun `typing a query debounces then filters results`() = runTest {
        val (viewModel, _) = buildViewModel()

        viewModel.uiState.test {
            awaitItem() // loading
            awaitItem() // initial full list

            viewModel.onQueryChanged("squ")
            advanceTimeBy(300) // clear the 250ms debounce window

            val filtered = expectMostRecentItem()
            assertThat(filtered.pokemons.map { it.name }).containsExactly("Squirtle")
        }
    }

    @Test
    fun `repository error surfaces as errorMessage`() = runTest {
        val (viewModel, repository) = buildViewModel()
        repository.errorToReturn = java.io.IOException("offline")

        viewModel.uiState.test {
            awaitItem() // loading
            val errorState = awaitItem()
            assertThat(errorState.errorMessage).isEqualTo("offline")
        }
    }
}
