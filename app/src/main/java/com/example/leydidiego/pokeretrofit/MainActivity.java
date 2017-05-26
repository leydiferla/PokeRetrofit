package com.example.leydidiego.pokeretrofit;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.leydidiego.pokeretrofit.models.Poder;
import com.example.leydidiego.pokeretrofit.models.Pokemon;
import com.example.leydidiego.pokeretrofit.models.PokemonRespuesta;
import com.example.leydidiego.pokeretrofit.models.PokemonRespuestaPoder;
import com.example.leydidiego.pokeretrofit.pokeapi.ListaPokemonAdapter;
import com.example.leydidiego.pokeretrofit.pokeapi.PokeapiService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private RecyclerView recyclerView;
    private ListaPokemonAdapter listaPokemonAdapter;

    private static final String TAG="POKEDEX";

    private int offset;
    private boolean aptoParaCargar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView;
        listaPokemonAdapter = new ListaPokemonAdapter(this);
        recyclerView.setAdapter(listaPokemonAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (aptoParaCargar) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, " Llegamos al final.");

                            aptoParaCargar = false;
                            offset += 20;
                            obtenerDatos(offset);
                        }
                    }
                }
            }
        });


        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        aptoParaCargar = true;
        offset = 0;
        obtenerDatos(offset);

        obtenerPoder();
    }


    private void obtenerDatos() {
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call<PokemonRespuesta> pokemonRespuestaCall = service.obtenerListaPokemon();

        pokemonRespuestaCall.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                if(response.isSuccessful()){
                    PokemonRespuesta pokemonRespuesta = response.body();

                    ArrayList<Pokemon> listaPokemon = pokemonRespuesta.getResults();

                    for(int i=0;i<listaPokemon.size();i++)
                    {
                        Pokemon p = listaPokemon.get(i);
                        Log.i(TAG," Pokemon: "+p.getName()+"url:"+p.getUrl());
                    }

                }else
                {
                    Log.e(TAG, "onResponse: "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                Log.e(TAG," onFailure: "+t.getMessage());
            }
        });
    }


    public  void obtenerPoder()
    {
        PokeapiService service = retrofit.create(PokeapiService.class);
        Call <PokemonRespuestaPoder> pokemonRespuestaPoderCall =  service.obtenerPoderPokemon();

        pokemonRespuestaPoderCall.enqueue(new Callback<PokemonRespuestaPoder>() {
            @Override
            public void onResponse(Call<PokemonRespuestaPoder> call, Response<PokemonRespuestaPoder> response) {
                if (response.isSuccessful())
                {
                    PokemonRespuestaPoder pokemonRespuestaPoder = response.body();
                    ArrayList<Poder> listaPoder = pokemonRespuestaPoder.getResults();

                    for (int i = 0; i < listaPoder.size();i++)
                    {
                        Poder p = listaPoder.get(i);
                        Log.i(TAG, "Poder " + p.getAbility());
                    }
                }
            }

            @Override
            public void onFailure(Call<PokemonRespuestaPoder> call, Throwable t) {

            }
        });

    }

    public void clickBoton1 (View v) {
        alerta("Se ha pulsado DialogBuilder");
    }
    public void alerta(String cadena) {
        //se prepara la alerta creando nueva instancia
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        //seleccionamos la cadena a mostrar
        dialogBuilder.setMessage(cadena);
        //elegimo un titulo y configuramos para que se pueda quitar
        dialogBuilder.setCancelable(true).setTitle("Titulo de la alerta");
        //mostramos el dialogBuilder
        dialogBuilder.create().show();
    }
}
