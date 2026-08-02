package com.example.pokedex.presentation.theme

import androidx.compose.ui.graphics.Color

val PokedexRed = Color(0xFFEE1515)
val PokedexRedDark = Color(0xFFB30000)
val PokedexYellow = Color(0xFFFFCB05)
val SurfaceLight = Color(0xFFF7F7F7)
val SurfaceDark = Color(0xFF1C1C1E)

val TypeColors = mapOf(
    "Grass" to Color(0xFF78C850),
    "Poison" to Color(0xFFA040A0),
    "Fire" to Color(0xFFF08030),
    "Water" to Color(0xFF6890F0),
    "Electric" to Color(0xFFF8D030),
    "Ice" to Color(0xFF98D8D8),
    "Flying" to Color(0xFFA890F0),
    "Psychic" to Color(0xFFF85888),
    "Bug" to Color(0xFFA8B820),
    "Normal" to Color(0xFFA8A878),
    "Fighting" to Color(0xFFC03028),
    "Ground" to Color(0xFFE0C068),
    "Rock" to Color(0xFFB8A038),
    "Ghost" to Color(0xFF705898),
    "Dragon" to Color(0xFF7038F8),
    "Dark" to Color(0xFF705848),
    "Steel" to Color(0xFFB8B8D0),
    "Fairy" to Color(0xFFEE99AC)
)

fun colorForType(type: String) = TypeColors[type] ?: Color(0xFF68A090)
