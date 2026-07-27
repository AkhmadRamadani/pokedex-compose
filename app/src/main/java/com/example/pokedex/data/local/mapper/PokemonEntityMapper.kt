package com.example.pokedex.data.local.mapper

import com.example.pokedex.data.local.entity.PokemonEntity
import com.example.pokedex.data.remote.dto.PokemonDto
import com.example.pokedex.domain.model.Pokemon

fun PokemonDto.toEntity(): PokemonEntity {
    val numericId = id?.removePrefix("#")?.toIntOrNull() ?: 0
    val rawIdStr = id ?: if (numericId > 0) "#${numericId.toString().padStart(3, '0')}" else ""
    val imgUrl = imageUrl ?: img
    val resolvedTypes = typeofpokemon ?: type ?: emptyList()
    val desc = xdescription ?: ydescription
    val evoIds = evolutions ?: emptyList()
    val nextEvosNames = nextEvolution?.map { it.name } ?: emptyList()
    val allEvoIdentifiers = if (evoIds.isNotEmpty()) evoIds else nextEvosNames

    return PokemonEntity(
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
        evolutionIds = allEvoIdentifiers
    )
}

fun List<PokemonEntity>.toDomain(): List<Pokemon> {
    val byIdString = mapNotNull { entity ->
        if (entity.idString.isNotBlank()) entity.idString to entity else null
    }.toMap()

    val byName = associateBy { it.name.lowercase() }

    return map { entity ->
        val evolutionEntities = when {
            entity.evolutionIds.isNotEmpty() -> {
                entity.evolutionIds
                    .filter { evoId -> evoId != entity.idString }
                    .mapNotNull { evoId -> byIdString[evoId] ?: byName[evoId.lowercase()] }
            }
            else -> emptyList()
        }

        Pokemon(
            id = entity.id,
            idString = entity.idString,
            name = entity.name,
            imageUrl = entity.imageUrl,
            description = entity.description,
            category = entity.category,
            types = entity.types,
            height = entity.height,
            weight = entity.weight,
            weaknesses = entity.weaknesses,
            abilities = entity.abilities,
            hp = entity.hp,
            attack = entity.attack,
            defense = entity.defense,
            specialAttack = entity.specialAttack,
            specialDefense = entity.specialDefense,
            speed = entity.speed,
            total = entity.total,
            evolvedFrom = entity.evolvedFrom,
            reason = entity.reason,
            evolutions = evolutionEntities.map { evo ->
                Pokemon(
                    id = evo.id,
                    idString = evo.idString,
                    name = evo.name,
                    imageUrl = evo.imageUrl,
                    description = evo.description,
                    category = evo.category,
                    types = evo.types,
                    height = evo.height,
                    weight = evo.weight,
                    weaknesses = evo.weaknesses,
                    abilities = evo.abilities,
                    hp = evo.hp,
                    attack = evo.attack,
                    defense = evo.defense,
                    specialAttack = evo.specialAttack,
                    specialDefense = evo.specialDefense,
                    speed = evo.speed,
                    total = evo.total,
                    evolvedFrom = evo.evolvedFrom,
                    reason = evo.reason,
                    evolutions = emptyList()
                )
            }
        )
    }
}
