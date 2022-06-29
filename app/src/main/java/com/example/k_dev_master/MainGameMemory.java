package com.example.k_dev_master;

import android.content.Context;

public class MainGameMemory {
    private final Context mContext;
    private final MainViewMemory mView;
    public Stage stage = null;
    public int currStageLevel;
    private long recordTime = 0;
    private long currTimer = 0;
    private static final int GAME_SHOWCARDS = -1;
    private static final int GAME_ONGOING = 0;
    private static final int GAME_DONE = 1;

    public int gameState = GAME_SHOWCARDS;



    public MainGameMemory(Context context, MainViewMemory view) {
        mContext = context;
        mView = view;
    }

    public void newGame() {
        if (stage == null) {
            stage = new Stage(4, 4, 1);
        } else {
            stage.clearCards(stage.field);
        }
        currStageLevel = 1;
        addCards();
        recordTime = 0;
        mView.invalidate();
    }

    /**
     * Needs to be fixed (new card should be randomly distributed.
     */
    public void addCards() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                stage.field[x][y] = new Card(x,y,3);
            }
        }
    }

    public int getStage() {
        return stage.getStageLevel();
    }


    public boolean stageWon(Stage stage) {
        for (int xx = 0; xx < 4; xx++) {
            for (int yy = 0; yy < 4; yy++) {
                if(stage.field[xx][yy].isHidden()) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
    if (stageWon(stage)) {
        stageUp()
    }
     */
    public void stageUp() {
        if (currStageLevel == 1) {
            stage.setStageLevel(2);

            currStageLevel = getStage();
            recordTime += currTimer;
            addCards();
            gameState = GAME_SHOWCARDS;
        } else if (currStageLevel == 2) {
            stage.setStageLevel(3);
            currStageLevel = getStage();
            recordTime += currTimer;
            addCards();
            gameState = GAME_SHOWCARDS;
        } else if (currStageLevel == 3) {
            gameState = GAME_DONE;
        }
        currTimer = 0;

    }


    // If all cards are opened at stage 1, we pass to stage2 and add the timer to recordTime
    // showCards -> after TIME_DISPLAY_STAGE1 (ms) -> hideCards -> GAME_ONGOING
    //

    public void showfield() {
        for (int xx = 0; xx < 4; xx++) {
            for (int yy = 0; yy < 4; yy++) {
                stage.field[xx][yy].setHidden(false);
            }
        }
    }
    public void hidefield() {
        for (int xx = 0; xx < 4; xx++) {
            for (int yy = 0; yy < 4; yy++) {
                stage.field[xx][yy].setHidden(true);
            }
        }
    }

    public void resetRecordTime() {
        recordTime = 0;
    }
    public long getRecordTime() {
        return recordTime;
    }
}