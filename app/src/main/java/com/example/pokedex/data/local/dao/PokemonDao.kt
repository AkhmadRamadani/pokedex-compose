package com.example.pokedex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokedex.data.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    fun getPokemons(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemons ORDER BY id ASC")
    suspend fun getPokemonsList(): List<PokemonEntity>

    @Query("SELECT * FROM pokemons WHERE id = :id")
    fun getPokemonById(id: Int): Flow<PokemonEntity?>

    @Query("SELECT * FROM pokemons WHERE id = :id")
    suspend fun getPokemonByIdDirect(id: Int): PokemonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemons: List<PokemonEntity>)

    @Query("DELETE FROM pokemons")
    suspend fun clearPokemons()
}
