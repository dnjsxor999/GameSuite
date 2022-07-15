package com.example.k_dev_master.gomoku;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class InputListener implements View.OnTouchListener{
    private final GomokuView mView;


    public InputListener(GomokuView view) {
        super();
        this.mView = view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mView.userX = (int) event.getX();
            mView.userY = (int) event.getY();

            mView.game.start = true;
/*
            for(int i = 0; i < mView.game.numSquaresX; i++) {
                for (int j = 0; j < mView.game.numSquaresY; j++) {
                    double boardWidth = 69.1;

                    int lowerRange_x = (int) (43 + boardWidth * i - (boardWidth / 2));
                    int higherRange_x = (int) (lowerRange_x + boardWidth);
                    int lowerRange_y = (int) (642 + boardWidth * j - (boardWidth / 2));
                    int higherRange_y = (int) (lowerRange_y + boardWidth);

                    if (mView.InRange(lowerRange_x, higherRange_x, mView.userX)
                            && mView.InRange(lowerRange_y, higherRange_y, mView.userY)) {
                        mView.game.stone_x = i;
                        mView.game.stone_y = j;
                    }
                }
            }
*/
            mView.game.stone_x = Math.round((mView.userX - 40) / 69);
            mView.game.stone_y = Math.round((mView.userY - 650) / 69);
            mView.game.board_x = (int)convertDpToPixel(16f, mView.game.getmContext()) + (24 * mView.getWidth() / 800)
                    + mView.game.stone_x * (42 * mView.getWidth() / 800);
            //mView.game.board_x = mView.userX;
            mView.game.board_y = mView.userY;

            if (mView.game.board.getStone(mView.game.stone_x, mView.game.stone_y) == null) {
                mView.game.addStone();
                mView.game.turn++;
            }
        }
        return true;
    }

    public static float convertDpToPixel(float dp, Context context){
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
