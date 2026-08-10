package com.example.pokedex.data.local.mapper

import com.example.pokedex.data.local.entity.PokemonEntity
import com.example.pokedex.data.remote.dto.PokemonDto
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PokemonEntityMapperTest {

    @Test
    fun `toEntity maps PokemonDto correctly`() {
        val dto = PokemonDto(
            id = "#001",
            name = "bulbasaur",
            imageUrl = "http://example.com/1.png",
            typeofpokemon = listOf("Grass", "Poison"),
            evolutions = listOf("#002")
        )

        val entity = dto.toEntity()

        assertThat(entity.id).isEqualTo(1)
        assertThat(entity.idString).isEqualTo("#001")
        assertThat(entity.name).isEqualTo("Bulbasaur")
        assertThat(entity.types).containsExactly("Grass", "Poison")
        assertThat(entity.evolutionIds).containsExactly("#002")
    }

    @Test
    fun `toDomain resolves evolutions from entities`() {
        val bulbasaurEntity = PokemonEntity(
            id = 1,
            idString = "#001",
            name = "Bulbasaur",
            imageUrl = null,
            description = "Seed pokemon",
            category = "Seed",
            types = listOf("Grass"),
            height = "0.7 m",
            weight = "6.9 kg",
            weaknesses = listOf("Fire"),
            abilities = listOf("Overgrow"),
            hp = 45, attack = 49, defense = 49,
            specialAttack = 65, specialDefense = 65, speed = 45,
            total = 318,
            evolvedFrom = null,
            reason = null,
            evolutionIds = listOf("#002")
        )

        val ivysaurEntity = PokemonEntity(
            id = 2,
            idString = "#002",
            name = "Ivysaur",
            imageUrl = null,
            description = "Flame pokemon",
            category = "Seed",
            types = listOf("Grass"),
            height = "1.0 m",
            weight = "13.0 kg",
            weaknesses = listOf("Fire"),
            abilities = listOf("Overgrow"),
            hp = 60, attack = 62, defense = 63,
            specialAttack = 80, specialDefense = 80, speed = 60,
            total = 405,
            evolvedFrom = "Bulbasaur",
            reason = "(Level 16)",
            evolutionIds = emptyList()
        )

        val domainList = listOf(bulbasaurEntity, ivysaurEntity).toDomain()

        assertThat(domainList).hasSize(2)
        val bulbasaur = domainList.first { it.id == 1 }
        assertThat(bulbasaur.evolutions).hasSize(1)
        assertThat(bulbasaur.evolutions.first().name).isEqualTo("Ivysaur")
    }
}
