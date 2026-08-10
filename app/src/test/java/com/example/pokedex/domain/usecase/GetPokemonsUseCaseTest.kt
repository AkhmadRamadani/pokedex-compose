package com.example.pokedex.domain.usecase

import com.example.pokedex.common.Result
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetPokemonsUseCaseTest {

    private lateinit var repository: FakePokemonRepository
    private lateinit var useCase: GetPokemonsUseCase

    @Before
    fun setUp() {
        repository = FakePokemonRepository()
        useCase = GetPokemonsUseCase(repository)
    }

    @Test
    fun `invoke with blank query returns all pokemons on success`() = runTest {
        val emissions = useCase().toList()

        assertThat(emissions.first()).isEqualTo(Result.Loading)
        val success = emissions.last() as Result.Success
        assertThat(success.data).hasSize(3)
    }

    @Test
    fun `invoke with query filters by name case-insensitively`() = runTest {
        val emissions = useCase(query = "char").toList()

        val success = emissions.last() as Result.Success
        assertThat(success.data).hasSize(1)
        assertThat(success.data.first().name).isEqualTo("Charmander")
    }

    @Test
    fun `invoke with query matching nothing returns empty success, not error`() = runTest {
        val emissions = useCase(query = "zzz-does-not-exist").toList()

        val success = emissions.last() as Result.Success
        assertThat(success.data).isEmpty()
    }

    @Test
    fun `invoke propagates repository error untouched`() = runTest {
        repository.errorToReturn = IllegalStateException("boom")

        val emissions = useCase().toList()

        val error = emissions.last() as Result.Error
        assertThat(error.message).isEqualTo("boom")
    }
}
