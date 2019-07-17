package com.hadadas.memorygame;

import android.os.Handler;
import android.util.Log;

import com.hadadas.memorygame.bord.Card;
import com.hadadas.memorygame.retrofit.CharacterResponse;
import com.hadadas.memorygame.retrofit.RickNMortyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Game {
    private String TAG = Game.class.getSimpleName();

    private static final int NUM_OF_PAIRS = 50;
    private final GameCallBacks gameCallBacks;
    private Retrofit retrofit;
    private int characterCount;
    private List<Integer> uniqueIds;
    private int attempsCounter;

    private Card flippedCards [];
    private int currentFlipHandCounter;

    public Game(GameCallBacks gameCallBacks) {
        this.gameCallBacks = gameCallBacks;
        initialize();
    }

    public void initialize() {
        resetAttemptsCounter();
        resetCurrentFlippedCards();
        initRetrofit();
    }

    private void reset(){
        Log.d(TAG, "reset");
        resetAttemptsCounter();
        resetCurrentFlippedCards();
        generateUniqueIds();
    }

    private void resetFlippedHandCounter(){
        currentFlipHandCounter = 0;
    }

    private void resetCurrentFlippedCards(){
        flippedCards = new Card[2];
    }

    private void resetAttemptsCounter(){
        attempsCounter = 0;
    }

    void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(RickNMortyService.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    void getCharactersCountRequest() {
        RickNMortyService service = retrofit.create(RickNMortyService.class);
        try {
            Log.d("Main", "get");
            final Call<CharacterResponse> info = service.getCharactersInfo();

            info.enqueue(new Callback<CharacterResponse>() {
                @Override
                public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                    characterCount = response.body().info.getCount();
                    gameCallBacks.onGetCountSuccess(characterCount);
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

    private int generateUniqueNumberWithRange(int max) {
        int min = 0;
        int random = new Random().nextInt((max - min) + 1) + min;
        return random;
    }

    void generateUniqueIds() {
        uniqueIds = new ArrayList<>();
        while (uniqueIds.size() < NUM_OF_PAIRS) {
            int id = generateUniqueNumberWithRange(characterCount);
            if(!uniqueIds.contains(id)){
                uniqueIds.add(id);
            }
        }
        Log.d(TAG, "uniqueIds :" + String.valueOf(uniqueIds));
        gameCallBacks.onFinishUniqueIds();
    }

    public int getCharacterCount(){
        return characterCount;
    }


    public List<Integer> getUniqueIds() {
        return uniqueIds;
    }

    public void cardHasFlipped(Card card, int position) {
        setCardFlipped(card, position);
        checkForMatch();
    }

    private void setCardFlipped(Card card, int position){
        card.setPosition(position);
        flippedCards[currentFlipHandCounter] = card;
        currentFlipHandCounter++;
    }

    private void checkForMatch(){
        if(currentFlipHandCounter < 2){
            return;
        }
        resetFlippedHandCounter();
        if(!flippedCards[0].equals(flippedCards[1])){
            attempsCounter++;
            Log.d(TAG, "attempsCounter: " + attempsCounter);
            if(attempsCounter >= NUM_OF_PAIRS){
                gameCallBacks.onAttemptsOver();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        reset();
                    }
                }, 1500);

            }else{
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gameCallBacks.onFailAttempt(flippedCards);
                    }
                }, 1500);
            }

        }else{
            gameCallBacks.onMatchFound();
        }
    }
}
