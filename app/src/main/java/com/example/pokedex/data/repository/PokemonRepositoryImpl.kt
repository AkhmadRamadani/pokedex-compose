package com.example.pokedex.data.repository

import com.example.pokedex.common.DispatcherProvider
import com.example.pokedex.common.Result
import com.example.pokedex.data.local.dao.PokemonDao
import com.example.pokedex.data.local.mapper.toDomain
import com.example.pokedex.data.local.mapper.toEntity
import com.example.pokedex.data.remote.PokemonApi
import com.example.pokedex.domain.model.Pokemon
import com.example.pokedex.domain.repository.PokemonRepository
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepositoryImpl @Inject constructor(
    private val api: PokemonApi,
    private val dao: PokemonDao,
    private val dispatchers: DispatcherProvider
) : PokemonRepository {

    private val cache = MutableStateFlow<List<Pokemon>>(emptyList())
    private val refreshMutex = Mutex()

    override fun getPokemons(): Flow<Result<List<Pokemon>>> = flow {
        emit(Result.Loading)

        val localEntities = dao.getPokemonsList()
        if (localEntities.isNotEmpty()) {
            val domainList = localEntities.toDomain()
            cache.value = domainList
            emit(Result.Success(domainList))
        }

        refreshMutex.withLock {
            try {
                val responseDtos = api.getPokemons()
                val entities = responseDtos.map { it.toEntity() }
                dao.insertPokemons(entities)

                val updatedEntities = dao.getPokemonsList()
                val updatedDomain = updatedEntities.toDomain()
                cache.value = updatedDomain
                emit(Result.Success(updatedDomain))
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                if (cache.value.isEmpty()) {
                    emit(Result.Error(e, e.friendlyMessage()))
                }
            }
        }
    }.flowOn(dispatchers.io)

    override fun getPokemonById(id: Int): Flow<Result<Pokemon>> =
        getPokemons().map { result ->
            when (result) {
                is Result.Loading -> Result.Loading
                is Result.Error -> result
                is Result.Success -> {
                    val pokemon = result.data.firstOrNull { it.id == id }
                    if (pokemon != null) {
                        Result.Success(pokemon)
                    } else {
                        Result.Error(NoSuchElementException("Pokemon $id not found"))
                    }
                }
            }
        }

    val cachedPokemons get() = cache.asStateFlow()

    private fun Throwable.friendlyMessage(): String = when (this) {
        is IOException -> "No internet connection. Check your network and retry."
        else -> message ?: "Unexpected error occurred."
    }
}
