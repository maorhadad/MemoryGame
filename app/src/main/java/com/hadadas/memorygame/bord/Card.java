package com.hadadas.memorygame.bord;

public class Card {
    private int id;
    private boolean isFront;

    public Card(int id) {
        this.id = id;
        isFront = false;
    }

    public int getId() {
        return id;
    }

    public boolean isFront() {
        return isFront;
    }

    public void toggle() {
        isFront = !isFront;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", isFront=" + isFront +
                '}';
    }
}
