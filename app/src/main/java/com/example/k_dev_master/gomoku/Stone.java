package com.example.k_dev_master.gomoku;

public class Stone {
    public GomokuView mView;

    private int x;
    private int y;
    private int board_x;
    private int board_y;
    private String color;

    public Stone(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.board_x = board_x;
        this.board_y = board_y;
    }

    public String getColor() { return this.color; }

    void setColor(String color) {this.color = color;}

    public int getX() {
        return this.x;
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

    public int getBoard_x() {
        return this.board_x;
    }

    void setBoard_x(int board_x) {
        this.board_x = board_x;
    }

    public int getBoard_y() {
        return this.board_y;
    }

    void setBoard_y(int board_y) {
        this.board_y = board_y;
    }
}
