package com.example.leydidiego.pokeretrofit.models;

import java.util.ArrayList;

/**
 * Created by leydidiego on 19/05/17.
 */

public class PokemonRespuesta {
    private ArrayList<Pokemon> results;

    public ArrayList<Pokemon> getResults() {
        return results;
    }

    public void setResults(ArrayList<Pokemon> results) {
        this.results = results;
    }
}
