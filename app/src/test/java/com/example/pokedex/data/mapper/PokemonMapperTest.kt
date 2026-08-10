package com.example.pokedex.data.mapper

import com.example.pokedex.data.remote.dto.PokemonDto
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PokemonMapperTest {

    @Test
    fun `toDomain maps JSON schema list and resolves evolutions as Pokemon models`() {
        val bulbasaurDto = PokemonDto(
            id = "#001",
            name = "Bulbasaur",
            imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/001.png",
            xdescription = "Bulbasaur can be seen napping...",
            typeofpokemon = listOf("Grass", "Poison"),
            weaknesses = listOf("Fire", "Flying"),
            evolutions = listOf("#001", "#002", "#003")
        )

        val ivysaurDto = PokemonDto(
            id = "#002",
            name = "Ivysaur",
            imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/002.png",
            typeofpokemon = listOf("Grass", "Poison"),
            evolutions = listOf("#001", "#002", "#003"),
            evolvedFrom = "#001",
            reason = "(Level 16)"
        )

        val venusaurDto = PokemonDto(
            id = "#003",
            name = "Venusaur",
            imageUrl = "https://assets.pokemon.com/assets/cms2/img/pokedex/full/003.png",
            typeofpokemon = listOf("Grass", "Poison"),
            evolutions = listOf("#001", "#002", "#003"),
            evolvedFrom = "#002",
            reason = "(Level 32)"
        )

        val dtoList = listOf(bulbasaurDto, ivysaurDto, venusaurDto)
        val domainList = dtoList.toDomain()

        assertThat(domainList).hasSize(3)

        val bulbasaur = domainList.first { it.id == 1 }
        assertThat(bulbasaur.name).isEqualTo("Bulbasaur")
        assertThat(bulbasaur.idString).isEqualTo("#001")
        assertThat(bulbasaur.types).containsExactly("Grass", "Poison")

        // Check evolutions consume Pokemon domain models
        assertThat(bulbasaur.evolutions).hasSize(2)
        val evoNames = bulbasaur.evolutions.map { it.name }
        assertThat(evoNames).containsExactly("Ivysaur", "Venusaur")

        val ivysaurEvo = bulbasaur.evolutions.first { it.name == "Ivysaur" }
        assertThat(ivysaurEvo.id).isEqualTo(2)
        assertThat(ivysaurEvo.idString).isEqualTo("#002")
        assertThat(ivysaurEvo.reason).isEqualTo("(Level 16)")
    }
}
