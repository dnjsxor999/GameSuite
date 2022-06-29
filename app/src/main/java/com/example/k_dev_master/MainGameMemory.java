package com.example.k_dev_master;

import android.content.Context;

public class MainGameMemory {
    private final Context mContext;
    private final MainViewMemory mView;
    public Stage stage = null;
    private long recordTime = 0;


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
        addCards();
        recordTime = 0;
        mView.invalidate();
    }

    /**
     * Needs to be fixed (new card should be randomly distributed)
     *
     */
    public void addCards() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                stage.field[x][y] = new Card(x,y,3);
            }
        }
    }


}