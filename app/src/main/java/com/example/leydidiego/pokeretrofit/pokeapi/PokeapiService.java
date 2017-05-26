package com.example.leydidiego.pokeretrofit.pokeapi;

import com.example.leydidiego.pokeretrofit.models.PokemonRespuesta;
import com.example.leydidiego.pokeretrofit.models.PokemonRespuestaPoder;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by leydidiego on 19/05/17.
 */

public interface PokeapiService {
    @GET("pokemon")
    Call<PokemonRespuesta> obtenerListaPokemon();

    Call<PokemonRespuestaPoder> obtenerPoderPokemon();
}
