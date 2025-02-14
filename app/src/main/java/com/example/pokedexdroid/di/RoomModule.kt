package com.example.pokedexdroid.di

import android.content.Context
import androidx.room.Room
import com.example.pokedexdroid.data.local.PokemonDao
import com.example.pokedexdroid.data.local.PokemonDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun getPokemonDataBase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        PokemonDataBase::class.java,
        "PokemonDataBase"
    ).build()

    @Provides
    @Singleton
    fun getPokemonDao(dataBase: PokemonDataBase): PokemonDao = dataBase.getPokemonDao()
}
