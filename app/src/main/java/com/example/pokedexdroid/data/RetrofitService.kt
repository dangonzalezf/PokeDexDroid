package com.example.pokedexdroid.data

import com.example.pokedexdroid.data.model.PokemonPage
import com.example.pokedexdroid.data.model.PokemonCardData
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    @GET("pokemon") // offset limit 1300, next is null
    suspend fun getPokemonPage(@Query("offset") offset: String = "0", @Query("limit") limit: String = "20"): PokemonPage

    @GET("pokemon/{name}")
    suspend fun getPokemonByName(@Path("name") name: String): PokemonCardData


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
