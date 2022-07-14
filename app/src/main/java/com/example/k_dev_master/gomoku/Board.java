package com.example.k_dev_master.gomoku;

import java.util.ArrayList;

public class Board {
    public Stone[][] field;
    private final int row = 19;
    private final int col = 19;

    public Board() {
        field = new Stone[row][col];
        clearBoard();
    }

    public Stone getStoneContent(int x, int y) {
        return field[x][y];
    }

    public Stone getStoneContent(Stone stone) {
        if (stone != null) {
            return field[stone.getX()][stone.getY()];
        } else {
            return null;
        }
    }

    public void insertStone(Stone stone) {
        field[stone.getX()][stone.getY()] = stone;
    }

    public ArrayList<Stone> getAvailableCells() {
        ArrayList<Stone> availableCells = new ArrayList<>();
        for (int xx = 0; xx < row; xx++) {
            for (int yy = 0; yy < col; yy++) {
                if (field[xx][yy] == null) {
                    availableCells.add(new Stone(xx, yy, ""));
                }
            }
        }
        return availableCells;
    }

    public void clearBoard() {
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < col; y++) {
                field[x][y] = null;
            }
        }
    }

    public boolean isCellsAvailable() {
        return (getAvailableCells().size() >= 1);
    }

    public boolean isCellAvailable(Stone cell) {
        return !isCellOccupied(cell);
    }

    public boolean isCellOccupied(Stone cell) { return (getStoneContent(cell) != null);}
}
