package com.example.pokedexdroid.di

import android.content.Context
import androidx.room.Room
import com.example.pokedexdroid.data.local.PokemonDao
import com.example.pokedexdroid.data.local.PokemonDataBase
import com.example.pokedexdroid.data.local.source.PokemonLocalDataSource
import com.example.pokedexdroid.data.model.repository.PokemonRemoteDataSource
import com.example.pokedexdroid.data.model.repository.PokemonRepository
import com.example.pokedexdroid.domain.GetAllPokemonUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class MainActivityModule {

    @Provides
    fun getAllPokemonUseCase(repository: PokemonRepository) = GetAllPokemonUseCase(repository)

    @Provides
    fun getLocalDataSource(pokemonDao: PokemonDao) = PokemonLocalDataSource(pokemonDao)

    @Provides
    fun getRemoteDataSource() = PokemonRemoteDataSource()

    @Provides
    fun getRepository(remoteDataSource: PokemonRemoteDataSource, localDataSource: PokemonLocalDataSource) =
        PokemonRepository(remoteDataSource, localDataSource)
}
