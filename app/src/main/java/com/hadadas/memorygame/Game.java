package com.hadadas.memorygame;

import android.util.Log;

import com.hadadas.memorygame.retrofit.CharacterResponse;
import com.hadadas.memorygame.retrofit.RickNMortyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Game {
    private String TAG = Game.class.getSimpleName();

    private static final int NUM_OF_PAIRS = 50;
    private final GameCallBacks gameCallBacks;
    Retrofit retrofit;
    int count;
    List<Integer> uniqueIds;

    public Game(GameCallBacks gameCallBacks) {
        this.gameCallBacks = gameCallBacks;
        initialize();
    }

    public void initialize() {
        initRetrofit();
    }

    void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(RickNMortyService.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    void getCharactersCount() {
        RickNMortyService service = retrofit.create(RickNMortyService.class);
        try {
            Log.d("Main", "get");
            final Call<CharacterResponse> info = service.getCharactersInfo();

            info.enqueue(new Callback<CharacterResponse>() {
                @Override
                public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                    count = response.body().info.getCount();
                    gameCallBacks.onGetCountSuccess(count);
                }

                @Override
                public void onFailure(Call<CharacterResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                    gameCallBacks.onGetCountFail();
                }
            });
        } catch (Exception e) {
            Log.d("Main", "catch");

            e.printStackTrace();
        }
    }

    int generateUniqueNumberWithRange(int max) {
        int min = 0;
        int random = new Random().nextInt((max - min) + 1) + min;
        return random;
    }

    void generateUniqueIds(int max) {
        uniqueIds = new ArrayList<>();
        while (uniqueIds.size() < NUM_OF_PAIRS) {
            int id = generateUniqueNumberWithRange(count);
            if(!uniqueIds.contains(id)){
                uniqueIds.add(id);
            }
        }
        Log.d(TAG, "uniqueIds :" + uniqueIds.size());
        gameCallBacks.onFinishUniqeIds();
    }
}
