package com.hadadas.memorygame.retrofit;


import retrofit2.Call;
import retrofit2.http.GET;

public interface RickNMortyService {

    String BaseUrl = "https://rickandmortyapi.com/api/";

    @GET("character")
    Call<CharacterResponse>  getCharactersInfo();
}