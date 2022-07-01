package com.example.k_dev_master;

public class Card {
    private int display;
    private String tag;
    private int check = 0;

    Card(int display, String tag) {
        this.display = display;
        this.tag = tag;
    }

    int getDisplay() {
        return display;
    }

    String getTag() {
        return tag;
    }

    int getCheck() {
        return check;
    }

    void setDisplay(int display) {
        this.display = display;
    }

    void setTag(String tag) {
        this.tag = tag;
    }

    void setCheck(int check) {
        this.check = check;
    }
}
