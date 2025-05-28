package com.example.turboaz.di

import com.example.turboaz.data.remote.CarApiService
import com.example.turboaz.data.repository.CarRepositoryImpl
import com.example.turboaz.domain.repository.CarRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCarApiService(): CarApiService {
        return Retrofit.Builder()
            .baseUrl(CarApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CarApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCarRepository(api: CarApiService): CarRepository {
        return CarRepositoryImpl(api)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetCarsUseCase(repository: CarRepository): GetCarsUseCase {
        return GetCarsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCarByIdUseCase(repository: CarRepository): GetCarByIdUseCase {
        return GetCarByIdUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSearchCarsUseCase(repository: CarRepository): SearchCarsUseCase {
        return SearchCarsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFilterCarsUseCase(repository: CarRepository): FilterCarsUseCase {
        return FilterCarsUseCase(repository)
    }
}
