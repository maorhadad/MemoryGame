package com.hadadas.memorygame;

import com.hadadas.memorygame.bord.Card;

public interface GameCallBacks {

    void onGetCountSuccess(int count);
    void onGetCountFail();
    void onFinishUniqueIds();
    void onFailAttempt(Card[] cardsToFlipBack);
    void onMatchFound();

    void onAttemptsOver();
}
