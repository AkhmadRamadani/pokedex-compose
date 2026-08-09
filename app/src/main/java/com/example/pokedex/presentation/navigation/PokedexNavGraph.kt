package com.example.pokedex.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokedex.presentation.detail.PokemonDetailScreen
import com.example.pokedex.presentation.list.PokemonListScreen

sealed class PokedexDestination(val route: String) {
    data object List : PokedexDestination("pokemon_list")
    data object Detail : PokedexDestination("pokemon_detail/{pokemonId}") {
        fun createRoute(pokemonId: Int) = "pokemon_detail/$pokemonId"
    }
}

@Composable
fun PokedexNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = PokedexDestination.List.route) {
        composable(PokedexDestination.List.route) {
            PokemonListScreen(
                onPokemonClick = { pokemon ->
                    navController.navigate(PokedexDestination.Detail.createRoute(pokemon.id))
                }
            )
        }

        composable(
            route = PokedexDestination.Detail.route,
            arguments = listOf(navArgument("pokemonId") { type = NavType.IntType })
        ) {
            PokemonDetailScreen(
                onBack = { navController.popBackStack() },
                onEvolutionClick = { evo ->
                    navController.navigate(PokedexDestination.Detail.createRoute(evo.id))
                }
            )
        }
    }
}
