package com.example.pokedex.domain.model

data class Pokemon(
    val id: Int,
    val idString: String = "",
    val name: String,
    val imageUrl: String?,
    val description: String? = null,
    val category: String? = null,
    val types: List<String>,
    val height: String?,
    val weight: String?,
    val weaknesses: List<String>,
    val abilities: List<String> = emptyList(),
    val hp: Int? = null,
    val attack: Int? = null,
    val defense: Int? = null,
    val specialAttack: Int? = null,
    val specialDefense: Int? = null,
    val speed: Int? = null,
    val total: Int? = null,
    val evolvedFrom: String? = null,
    val reason: String? = null,
    val evolutions: List<Pokemon> = emptyList()
) {
    val primaryType: String get() = types.firstOrNull() ?: "Unknown"
}

