pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Pokedex"
include(":app")

// NOTE: This project is intentionally kept as a single Gradle module for
// simplicity, but the source set is organized by Clean Architecture layers
// (data / domain / presentation / di / common). Each top-level package below
// `com.example.pokedex` maps 1:1 to what would become its own Gradle module
// (":core:network", ":core:designsystem", ":feature:pokemon-list",
// ":feature:pokemon-detail", ":domain", ":data") in a true multi-module setup.
// See README.md -> "Scaling to multi-module" for the migration recipe.
