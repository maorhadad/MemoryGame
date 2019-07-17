package com.hadadas.memorygame;

public interface GameCallBacks {

    public void onGetCountSuccess(int count);
    public void onGetCountFail();
    void onFinishUniqeIds();
}
