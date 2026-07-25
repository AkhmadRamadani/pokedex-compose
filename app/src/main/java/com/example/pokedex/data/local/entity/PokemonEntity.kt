package com.example.pokedex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey
    val id: Int,
    val idString: String,
    val name: String,
    val imageUrl: String?,
    val description: String?,
    val category: String?,
    val types: List<String>,
    val height: String?,
    val weight: String?,
    val weaknesses: List<String>,
    val abilities: List<String>,
    val hp: Int?,
    val attack: Int?,
    val defense: Int?,
    val specialAttack: Int?,
    val specialDefense: Int?,
    val speed: Int?,
    val total: Int?,
    val evolvedFrom: String?,
    val reason: String?,
    val evolutionIds: List<String>
)
