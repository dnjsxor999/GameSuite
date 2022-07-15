package com.example.k_dev_master.gomoku;

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

            mView.game.stone_x = Math.round((mView.userX - 40) / 69);
            mView.game.stone_y = Math.round(mView.userY - 650) / 69;
            mView.game.board_x = mView.userX;
            mView.game.board_y = mView.userY;

            mView.game.addStone();

            mView.game.turn = mView.game.turn + 1;

            System.out.println("X: " + mView.userX);
            System.out.println("Y: " + mView.userY);
            System.out.println("stone_X: " + mView.game.stone_x);
            System.out.println("stone_Y: " + mView.game.stone_y);

        }
        return true;
    }
}
