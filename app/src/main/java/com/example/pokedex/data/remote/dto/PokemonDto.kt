package com.example.pokedex.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonResponseDto(
    @Json(name = "pokemon") val pokemon: List<PokemonDto> = emptyList()
)

@JsonClass(generateAdapter = true)
data class PokemonDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "name") val name: String = "",
    @Json(name = "imageurl") val imageUrl: String? = null,
    @Json(name = "img") val img: String? = null,
    @Json(name = "xdescription") val xdescription: String? = null,
    @Json(name = "ydescription") val ydescription: String? = null,
    @Json(name = "height") val height: String? = null,
    @Json(name = "category") val category: String? = null,
    @Json(name = "weight") val weight: String? = null,
    @Json(name = "typeofpokemon") val typeofpokemon: List<String>? = null,
    @Json(name = "type") val type: List<String>? = null,
    @Json(name = "weaknesses") val weaknesses: List<String> = emptyList(),
    @Json(name = "evolutions") val evolutions: List<String>? = null,
    @Json(name = "next_evolution") val nextEvolution: List<EvolutionDto>? = null,
    @Json(name = "abilities") val abilities: List<String> = emptyList(),
    @Json(name = "hp") val hp: Int? = null,
    @Json(name = "attack") val attack: Int? = null,
    @Json(name = "defense") val defense: Int? = null,
    @Json(name = "special_attack") val specialAttack: Int? = null,
    @Json(name = "special_defense") val specialDefense: Int? = null,
    @Json(name = "speed") val speed: Int? = null,
    @Json(name = "total") val total: Int? = null,
    @Json(name = "male_percentage") val malePercentage: String? = null,
    @Json(name = "female_percentage") val femalePercentage: String? = null,
    @Json(name = "genderless") val genderless: Int? = null,
    @Json(name = "cycles") val cycles: String? = null,
    @Json(name = "egg_groups") val eggGroups: String? = null,
    @Json(name = "evolvedfrom") val evolvedFrom: String? = null,
    @Json(name = "reason") val reason: String? = null,
    @Json(name = "base_exp") val baseExp: String? = null
)

@JsonClass(generateAdapter = true)
data class EvolutionDto(
    @Json(name = "num") val num: String,
    @Json(name = "name") val name: String
)

