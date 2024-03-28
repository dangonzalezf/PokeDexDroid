package com.example.pokedexdroid.data

import com.example.pokedexdroid.data.model.PokemonListRemoteResult
import com.example.pokedexdroid.data.model.PokemonRemoteResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {

    @GET("pokemon")
    suspend fun getPokemonList(): PokemonListRemoteResult

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): PokemonRemoteResult


    object RetrofitFactoryService {
        fun createService(): RetrofitService {
            return Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RetrofitService::class.java)
        }
    }
}
