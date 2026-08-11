# Pokedex App

A modern Android application built with **Jetpack Compose**, **Clean Architecture**, **MVVM**, **Hilt**, **Retrofit**, **Coroutines / Flow**, and **Room Local Storage**.

## Features

- 📱 **Single Activity Architecture**: Built with Jetpack Compose & Navigation Compose.
- ⚡ **Offline-First Storage**: Local database caching powered by **Room** for instant loading and offline access.
- 🔍 **Search & Filter**: Real-time Pokémon search with coroutine debouncing.
- 🎨 **Modern Material 3 UI**: Clean cards, custom type chips, and detailed stats view.
- 🏗️ **Clean Architecture**: Decoupled `presentation`, `domain`, `data`, and `di` layers.
- 🧪 **Unit Test Suite**: Tests covering ViewModels, Use Cases, Mappers, and Repository caching.

## Tech Stack & Libraries

- **UI**: Jetpack Compose, Material 3, Coil
- **Architecture**: MVVM, Clean Architecture
- **Dependency Injection**: Hilt (Dagger)
- **Local Persistence**: Room Database (KSP)
- **Networking**: Retrofit, Moshi, OkHttp
- **Asynchronous**: Kotlin Coroutines & Flow
- **Testing**: JUnit 4, Turbine, MockK, Google Truth

## Getting Started

1. Open project root in **Android Studio**.
2. Sync Gradle dependencies (`./gradlew build`).
3. Run the `app` target on an emulator or Android device (Android 7.0 / API 24+).

## Running Tests

Execute unit tests with:
```bash
./gradlew test
```
