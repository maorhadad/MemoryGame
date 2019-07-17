package com.hadadas.memorygame.bord;

import java.util.Objects;

public class Card {
    private int id;
    private boolean isFront;
    private int position;

    public Card(int id) {
        this.id = id;
        isFront = false;
    }

    public Card(Card card){
        this.id = card.getId();
        isFront = card.isFront;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return id == card.id && position != card.position;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", isFront=" + isFront +
                '}';
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}
