package com.example.k_dev_master;

import android.content.Context;

public class MainGame2048 {
<<<<<<< Updated upstream
    public static final int SPAWN_ANIMATION = -1;
    public static final int MOVE_ANIMATION = 0;
    public static final int MERGE_ANIMATION = 1;

    public static final int FADE_GLOBAL_ANIMATION = 0;
    private static final long MOVE_ANIMATION_TIME = MainView2048.BASE_ANIMATION_TIME;
    private static final long SPAWN_ANIMATION_TIME = MainView2048.BASE_ANIMATION_TIME;
    private static final long NOTIFICATION_DELAY_TIME = MOVE_ANIMATION_TIME + SPAWN_ANIMATION_TIME;
    private static final long NOTIFICATION_ANIMATION_TIME = MainView2048.BASE_ANIMATION_TIME * 5;
=======

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
    private static final int GAME_ENDLESS = 2;
    private static final int GAME_ENDLESS_WON = 3;
=======
//    private static final int GAME_ENDLESS = 2;
//    private static final int GAME_ENDLESS_WON = 3;
>>>>>>> Stashed changes
    private static final String HIGH_SCORE = "high score";
    private static final String FIRST_RUN = "first run";
    final int numSquaresX = 4;
    final int numSquaresY = 4;
    private final Context mContext;
    private final MainView2048 mView;
    public Grid grid = null;
<<<<<<< Updated upstream
    public AnimationGrid aGrid;
    public boolean canUndo;
    public long score = 0;
    public long highScore = 0;
    public long lastScore = 0;
    private long bufferScore = 0;
=======
//    public AnimationGrid aGrid;
    public boolean canUndo;
    public int score = 0;
    public int lastScore = 0;
    public int bufferScore = 0;
>>>>>>> Stashed changes

    public MainGame2048(Context context, MainView2048 view) {
        mContext = context;
        mView = view;
    }
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
    public void newGame() {
        if (grid == null) {
            grid = new Grid(numSquaresX, numSquaresY);
        } else {
            prepareUndoState();
            saveUndoState();
            grid.clearGrid();
        }
<<<<<<< Updated upstream
        aGrid = new AnimationGrid(numSquaresX, numSquaresY);
=======
//        aGrid = new AnimationGrid(numSquaresX, numSquaresY);
>>>>>>> Stashed changes
        // animation*
//        highScore = getHighScore();
//        if (score >= highScore) {
//            highScore = score;
<<<<<<< Updated upstream
//            recordHighScore();
=======
>>>>>>> Stashed changes
//        }
        score = 0;
        gameState = GAME_NORMAL;
        addStartTiles();
//        mView.showHelp = firstRun();
//        mView.refreshLastTime = true;
//        mView.resyncTime();
<<<<<<< Updated upstream
        mView.invalidate();
=======
//        mView.invalidate();
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
        aGrid.startAnimation(cell.getX(), cell.getY(), cell.getValue(), SPAWN_ANIMATION,
                SPAWN_ANIMATION_TIME, MOVE_ANIMATION_TIME, null); //Direction: -1 = EXPANDING
=======
//        aGrid.startAnimation(cell.getX(), cell.getY(), cell.getValue(), SPAWN_ANIMATION,
//                SPAWN_ANIMATION_TIME, MOVE_ANIMATION_TIME, null); //Direction: -1 = EXPANDING
>>>>>>> Stashed changes
        // animation*
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
<<<<<<< Updated upstream
=======
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
>>>>>>> Stashed changes

    /**
     * Cells are actually moved to direction and merged.
     * @param direction
     */
    public void move(int direction) {
<<<<<<< Updated upstream
        aGrid.cancelAnimations();
=======
//        aGrid.cancelAnimations();
>>>>>>> Stashed changes
        // animation*
        prepareUndoState();

        //move direction :
        // 0: up, 1: right, 2: down, 3: left
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                Cell temp = grid.getCellContent(x, y);
                if (direction == 3 && y != 0) { // when press left direction
                    int final_index = y;
                    boolean merged = false;
                    for (int j = y - 1; j >= 0; j--) {
                         if (grid.getCellContent(x, j) != null) {
                             final_index = j + 1;
                             if (grid.getCellContent(x, j).getValue() == temp.getValue()) {
                                 grid.removeCell(temp);
                                 Cell cell = new Cell(x, j, 2 * temp.getValue());
                                 grid.insertCell(cell);
                                 merged = true;
<<<<<<< Updated upstream
=======
                                 score += temp.getValue();
>>>>>>> Stashed changes
                             }
                             break;
                         }
                    }
                    if (!merged) {
                        grid.removeCell(temp);
                        temp.setY(final_index);
                        grid.insertCell(temp);
                    }
                } else if (direction == 2 && x != 3) { // when press down direction
                    int final_index = x;
                    boolean merged = false;
                    for (int i = x + 1; i < 4; i++) {
                        if (grid.getCellContent(i, y) != null) {
                            final_index = i - 1;
                            if (grid.getCellContent(i, y).getValue() == temp.getValue()) {
                                grid.removeCell(temp);
                                Cell cell = new Cell(i, y, 2 * temp.getValue());
                                grid.insertCell(cell);
                                merged = true;
<<<<<<< Updated upstream
=======
                                score += temp.getValue();
>>>>>>> Stashed changes
                            }
                            break;
                        }
                    }
                    if (!merged) {
                        grid.removeCell(temp);
                        temp.setY(final_index);
                        grid.insertCell(temp);
                    }
                } else if (direction == 1 && y != 3) { // when press down direction
                    int final_index = y;
                    boolean merged = false;
                    for (int j = y + 1; j < 4; j++) {
                        if (grid.getCellContent(x, j) != null) {
                            final_index = j - 1;
                            if (grid.getCellContent(x, j).getValue() == temp.getValue()) {
                                grid.removeCell(temp);
                                Cell cell = new Cell(x, j, 2 * temp.getValue());
                                grid.insertCell(cell);
                                merged = true;
<<<<<<< Updated upstream
=======
                                score += temp.getValue();
>>>>>>> Stashed changes
                            }
                            break;
                        }
                    }
                    if (!merged) {
                        grid.removeCell(temp);
                        temp.setY(final_index);
                        grid.insertCell(temp);
                    }
                } else if (direction == 0 && x != 0) { // when press up direction
                    int final_index = x;
                    boolean moved = false;
                    for (int i = x - 1; i >= 0; i--) {
                        if (grid.getCellContent(i, y) != null) {
                            final_index = i - 1;
                            if (grid.getCellContent(i, y).getValue() == temp.getValue()) {
                                grid.removeCell(temp);
                                Cell cell = new Cell(i, y, 2 * temp.getValue());
                                grid.insertCell(cell);
                                moved = true;
<<<<<<< Updated upstream
=======
                                score += temp.getValue();

>>>>>>> Stashed changes
                            }
                            break;
                        }
                    }
                    if (!moved) {
                        grid.removeCell(temp);
                        temp.setY(final_index);
                        grid.insertCell(temp);
                    }
                }

        // Merge and realigning cells depending on direction
            }
        }
    }
<<<<<<< Updated upstream
=======

    public int getScore() {
        return score;
    }
>>>>>>> Stashed changes
}