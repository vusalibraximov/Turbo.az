# Turbo.az Android App

A modern Android application for browsing and searching cars in Azerbaijan's largest automotive marketplace.

## Features

- Browse cars with detailed information
- Search cars by various criteria
- Filter cars by price, year, fuel type, and transmission
- View detailed car information including specifications and seller details
- Contact sellers directly through the app
- Modern Material Design UI
- Clean Architecture implementation

## Tech Stack

- **Language**: Kotlin
- **Architecture**: Clean Architecture with MVVM pattern
- **DI**: Dagger Hilt
- **Navigation**: Android Navigation Component
- **Network**: Retrofit with OkHttp
- **Image Loading**: Glide
- **Async Operations**: Kotlin Coroutines & Flow
- **UI Components**: Material Design Components
- **Build System**: Gradle with Kotlin DSL

## Architecture

The application follows Clean Architecture principles and is divided into three layers:

- **Presentation Layer**: Contains UI components (Activities, Fragments) and ViewModels
- **Domain Layer**: Contains business logic and use cases
- **Data Layer**: Contains repository implementations and data sources

## Project Structure

```
app/
├── data/
│   ├── remote/
│   │   ├── dto/
│   │   └── CarApiService.kt
│   └── repository/
│       └── CarRepositoryImpl.kt
├── domain/
│   ├── model/
│   │   └── Car.kt
│   ├── repository/
│   │   └── CarRepository.kt
│   └── usecase/
│       └── GetCarsUseCase.kt
└── presentation/
    ├── car_list/
    │   ├── CarListFragment.kt
    │   ├── CarListViewModel.kt
    │   └── adapter/
    │       └── CarListAdapter.kt
    └── car_detail/
        ├── CarDetailFragment.kt
        └── CarDetailViewModel.kt
```

## Setup

1. Clone the repository
2. Open the project in Android Studio
3. Sync project with Gradle files
4. Run the app on an emulator or physical device

## Requirements

- Android Studio Arctic Fox or newer
- Android SDK 24 or higher
- Gradle 7.4.2 or higher

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
