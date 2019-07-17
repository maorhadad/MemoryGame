package com.hadadas.memorygame.retrofit;

import com.google.gson.annotations.SerializedName;

public class Info {
    @SerializedName("count")
    int count;
    @SerializedName("pages")
    int pages;


    public int getCount() {
        return count;
    }

    public int getPages() {
        return pages;
    }

    @Override
    public String toString() {
        return "CharacterResponse{" +
                "count=" + count +
                ", pages=" + pages +
                '}';
    }
}
