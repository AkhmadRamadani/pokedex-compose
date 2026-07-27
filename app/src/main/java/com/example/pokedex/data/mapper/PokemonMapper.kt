package com.example.pokedex.data.mapper

import com.example.pokedex.data.remote.dto.PokemonDto
import com.example.pokedex.domain.model.Pokemon

fun PokemonDto.toDomain(): Pokemon {
    val numericId = id?.removePrefix("#")?.toIntOrNull() ?: 0
    val rawIdStr = id ?: if (numericId > 0) "#${numericId.toString().padStart(3, '0')}" else ""
    val imgUrl = imageUrl ?: img
    val resolvedTypes = typeofpokemon ?: type ?: emptyList()
    val desc = xdescription ?: ydescription

    return Pokemon(
        id = numericId,
        idString = rawIdStr,
        name = name.replaceFirstChar { it.uppercase() },
        imageUrl = imgUrl,
        description = desc,
        category = category,
        types = resolvedTypes,
        height = height,
        weight = weight,
        weaknesses = weaknesses,
        abilities = abilities,
        hp = hp,
        attack = attack,
        defense = defense,
        specialAttack = specialAttack,
        specialDefense = specialDefense,
        speed = speed,
        total = total,
        evolvedFrom = evolvedFrom,
        reason = reason,
        evolutions = emptyList()
    )
}

fun List<PokemonDto>.toDomain(): List<Pokemon> {
    val initialList = map { dto -> dto to dto.toDomain() }

    val byStringId = initialList.mapNotNull { (dto, pokemon) ->
        val key = dto.id ?: if (pokemon.id > 0) "#${pokemon.id.toString().padStart(3, '0')}" else null
        if (key != null) key to pokemon else null
    }.toMap()

    val byName = initialList.associate { (_, pokemon) -> pokemon.name.lowercase() to pokemon }

    return initialList.map { (dto, pokemon) ->
        val evolutionIds = dto.evolutions ?: emptyList()
        val nextEvosNames = dto.nextEvolution?.map { it.name } ?: emptyList()

        val evolutionModels = when {
            evolutionIds.isNotEmpty() -> {
                evolutionIds
                    .filter { evoId -> evoId != dto.id }
                    .mapNotNull { evoId -> byStringId[evoId] }
            }
            nextEvosNames.isNotEmpty() -> {
                nextEvosNames.mapNotNull { name -> byName[name.lowercase()] }
            }
            else -> emptyList()
        }

        pokemon.copy(
            evolutions = evolutionModels.map { evo -> evo.copy(evolutions = emptyList()) }
        )
    }
}

