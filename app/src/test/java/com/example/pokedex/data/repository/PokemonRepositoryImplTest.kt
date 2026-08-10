package com.example.pokedex.data.repository

import app.cash.turbine.test
import com.example.pokedex.common.Result
import com.example.pokedex.common.TestDispatcherProvider
import com.example.pokedex.data.local.dao.PokemonDao
import com.example.pokedex.data.local.entity.PokemonEntity
import com.example.pokedex.data.remote.PokemonApi
import com.example.pokedex.data.remote.dto.PokemonDto
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

class PokemonRepositoryImplTest {

    private val api: PokemonApi = mockk()
    private val dao: PokemonDao = mockk(relaxed = true)
    private val dispatchers = TestDispatcherProvider()

    private lateinit var repository: PokemonRepositoryImpl

    @Before
    fun setup() {
        repository = PokemonRepositoryImpl(api, dao, dispatchers)
    }

    @Test
    fun `getPokemons fetches from api and saves to local dao`() = runTest {
        val dto = PokemonDto(id = "#001", name = "bulbasaur")
        coEvery { dao.getPokemonsList() } returns emptyList() andThen listOf(
            PokemonEntity(1, "#001", "Bulbasaur", null, null, null, emptyList(), null, null, emptyList(), emptyList(), null, null, null, null, null, null, null, null, null, emptyList())
        )
        coEvery { api.getPokemons(any()) } returns listOf(dto)

        repository.getPokemons().test {
            assertThat(awaitItem()).isInstanceOf(Result.Loading::class.java)
            val successItem = awaitItem()
            assertThat(successItem).isInstanceOf(Result.Success::class.java)
            val pokemons = (successItem as Result.Success).data
            assertThat(pokemons).hasSize(1)
            assertThat(pokemons.first().name).isEqualTo("Bulbasaur")
            awaitComplete()
        }

        coVerify { dao.insertPokemons(any()) }
    }

    @Test
    fun `getPokemons returns cached local data when offline`() = runTest {
        val cachedEntity = PokemonEntity(1, "#001", "Bulbasaur", null, null, null, emptyList(), null, null, emptyList(), emptyList(), null, null, null, null, null, null, null, null, null, emptyList())
        coEvery { dao.getPokemonsList() } returns listOf(cachedEntity)
        coEvery { api.getPokemons(any()) } throws IOException("No network")

        repository.getPokemons().test {
            assertThat(awaitItem()).isInstanceOf(Result.Loading::class.java)
            val localSuccess = awaitItem()
            assertThat(localSuccess).isInstanceOf(Result.Success::class.java)
            val pokemons = (localSuccess as Result.Success).data
            assertThat(pokemons).hasSize(1)
            assertThat(pokemons.first().name).isEqualTo("Bulbasaur")
            awaitComplete()
        }
    }
}
