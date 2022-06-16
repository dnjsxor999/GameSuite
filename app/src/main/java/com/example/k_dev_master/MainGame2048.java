package com.example.k_dev_master;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;


public class MainGame2048 {

    private static final int maxValue = 2048;
    //Odd state = game is not active
    //Even state = game is active
    //Win state = active state + 1
    private static final int GAME_WIN = 1;
    private static final int GAME_LOST = -1;
    private static final int GAME_NORMAL = 0;
    public int gameState = GAME_NORMAL;
    public int lastGameState = GAME_NORMAL;
    private int bufferGameState = GAME_NORMAL;
    private static final int GAME_ENDLESS = 2;
    private static final int GAME_ENDLESS_WON = 3;
//    private static final int GAME_ENDLESS = 2;
//    private static final int GAME_ENDLESS_WON = 3;
    private static final String HIGH_SCORE = "high score";
    private static final String FIRST_RUN = "first run";
    final int numSquaresX = 4;
    final int numSquaresY = 4;
    private final Context mContext;
    private final MainView2048 mView;
    public Grid grid = null;
    public AnimationGrid aGrid;
    public boolean canUndo;

    public int score = 0;
    public int lastScore = 0;
    public int bufferScore = 0;
    public MainGame2048(Context context, MainView2048 view) {
        mContext = context;
        mView = view;
    }

    public void newGame() {
        if (grid == null) {
            grid = new Grid(numSquaresX, numSquaresY);
        } else {
            prepareUndoState();
            saveUndoState();
            grid.clearGrid();
        }

        score = 0;
        gameState = GAME_NORMAL;
        addStartTiles();
//        mView.showHelp = firstRun();
//        mView.refreshLastTime = true;
//        mView.resyncTime();
        mView.invalidate();
//        mView.invalidate();
    }


    /**
     * Adds two cells when we start new game.
     */
    private void addStartTiles() {
        int startTiles = 2;
        for (int xx = 0; xx < startTiles; xx++) {
            if (grid.isCellsAvailable()) {
                int value = Math.random() < 0.9 ? 2 : 4;
                Cell cell = grid.randomAvailableCell();
                cell.setValue(value);
                spawnCell(cell);
            }
        }
    }

    /**
     * Generate new cells
     * @param cell containing location and value
     */
    private void spawnCell(Cell cell) {
        grid.insertCell(cell);
    }

    private void saveUndoState() {
        grid.saveCells();
        canUndo = true;
        lastScore = bufferScore;
        lastGameState = bufferGameState;
    }

    private void prepareUndoState() {
        grid.prepareSaveCells();
        bufferScore = score;
        bufferGameState = gameState;
    }
    public void revertUndoState() {
        if (canUndo) {
            canUndo = false;
//            aGrid.cancelAnimations();
            grid.revertCell();
            score = lastScore;
            gameState = lastGameState;
//            mView.refreshLastTime = true;
//            mView.invalidate();
        }
    }

    public boolean gameWon() {
        return (gameState > 0 && gameState % 2 != 0);
    }

    public boolean gameLost() {
        return (gameState == GAME_LOST);
    }

    public boolean isActive() {
        return !(gameWon() || gameLost());
    }

    /**
     * Cells are actually moved to direction and merged.
     * @param direction
     */
    public void move(int direction) {
        // animation*
        prepareUndoState();
        //move direction :
        // 0: up, 1: right, 2: down, 3: left
        boolean moved = false; // got input but there is no change on grid
        if (direction == 0 || direction == 3) {
            for (int x = 0; x < 4; x++) {
                for (int y = 0; y < 4; y++) {
                    Cell temp = grid.getCellContent(x, y);
                    if (temp != null) {
                        if (temp.getValue() == 2048) gameWon();
                        if (direction == 0 && y != 0) { // when press up direction
                            int final_index = 0;
                            int moved_buffer = temp.getY();
                            boolean merged = false;
                            for (int j = y - 1; j >= 0; j--) {
                                if (grid.getCellContent(x, j) != null) {
                                    final_index = j + 1;
                                    if (grid.getCellContent(x, j).getValue() == temp.getValue()) {
                                        grid.removeCell(temp);
                                        Cell cell = new Cell(x, j, 2 * temp.getValue());
                                        grid.insertCell(cell);
                                        merged = true;
                                        moved = true;

                                        score += temp.getValue();
                                    }
                                    break;
                                }
                            }
                            if (!merged) {
                                grid.removeCell(temp);
                                temp.setY(final_index);
                                grid.insertCell(temp);
                                moved = true;
                            }
                            if (!merged && temp.getY() == moved_buffer) moved = false;
                        } else if (direction == 3 && x != 0) { // when press left direction
                            int final_index = 0;
                            int moved_buffer = temp.getX();
                            boolean merged = false;
                            for (int i = x - 1; i >= 0; i--) {
                                if (grid.getCellContent(i, y) != null) {
                                    final_index = i + 1;
                                    if (grid.getCellContent(i, y).getValue() == temp.getValue()) {
                                        grid.removeCell(temp);
                                        Cell cell = new Cell(i, y, 2 * temp.getValue());
                                        grid.insertCell(cell);
                                        merged = true;
                                        moved = true;

                                        score += temp.getValue();
                                    }
                                    break;
                                }
                            }
                            if (!merged) {
                                grid.removeCell(temp);
                                temp.setX(final_index);
                                grid.insertCell(temp);
                                moved = true;
                            }
                            if (!merged && temp.getX() == moved_buffer) moved = false;
                        }
                    }
                }
            }
        } else {
            for (int x = 3; x >= 0; x--) {
                for (int y = 3; y >= 0; y--) {
                    Cell temp = grid.getCellContent(x, y);
                    if (temp != null) {
                        if (temp.getValue() == 2048) gameWon();
                        if (direction == 1 && x != 3) {
                            int final_index = 3;
                            int moved_buffer = temp.getX();
                            boolean merged = false;
                            for (int i = x + 1; i < 4; i++) {
                                if (grid.getCellContent(i, y) != null) {
                                    final_index = i - 1;
                                    if (grid.getCellContent(i, y).getValue() == temp.getValue()) {
                                        grid.removeCell(temp);
                                        Cell cell = new Cell(i, y, 2 * temp.getValue());
                                        grid.insertCell(cell);
                                        merged = true;
                                        moved = true;
                                        score += temp.getValue();
                                    }
                                    break;
                                }
                            }
                            if (!merged) {
                                grid.removeCell(temp);
                                temp.setX(final_index);
                                grid.insertCell(temp);
                                moved = true;
                            }
                            if (!merged && temp.getX() == moved_buffer) moved = false;
                        } else if (direction == 2 && y != 3) {
                            int final_index = 3;
                            int moved_buffer = temp.getY();
                            boolean merged = false;
                            for (int j = y + 1; j < 4; j++) {
                                if (grid.getCellContent(x, j) != null) {
                                    final_index = j - 1;
                                    if (grid.getCellContent(x, j).getValue() == temp.getValue()) {
                                        grid.removeCell(temp);
                                        Cell cell = new Cell(x, j, 2 * temp.getValue());
                                        grid.insertCell(cell);
                                        merged = true;
                                        moved = true;

                                        score += temp.getValue();
                                    }
                                    break;
                                }
                            }
                            if (!merged) {
                                grid.removeCell(temp);
                                temp.setY(final_index);
                                grid.insertCell(temp);
                                moved = true;
                            }
                            if (!merged && temp.getY() == moved_buffer) moved = false;
                        }
                    }
                }
            }
        }
        if (moved && grid.isCellsAvailable()) {
            int value = Math.random() < 0.9 ? 2 : 4;
            Cell cell = grid.randomAvailableCell();
            cell.setValue(value);
            spawnCell(cell);
        }
        if (!grid.isCellsAvailable()) gameLost();
    }

    public int getScore() {
        return score;
    }
}