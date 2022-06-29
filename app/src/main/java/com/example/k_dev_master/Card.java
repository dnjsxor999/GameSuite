package com.example.k_dev_master;

public class Card {
    private boolean hidden = true;
    private int value;
    private int x;
    private int y;

    /**
     * Constructor.
     */
    public Card(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public int getX() {
        return this.x;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    void setY(int y) {
        this.y = y;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return hidden;
    }
}
