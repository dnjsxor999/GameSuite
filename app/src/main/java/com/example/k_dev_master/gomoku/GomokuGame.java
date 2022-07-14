package com.example.k_dev_master.gomoku;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class GomokuGame extends AppCompatActivity {
    private final Context mContext;
    private final GomokuView mView;
    public Board board = null;
    public final int numSquaresX = 19;
    public final int numSquaresY = 19;
    public int turn = 0;
    //public boolean winning = false;
    public boolean start = false;

    public int stone_x;
    public int stone_y;

    public int board_x;
    public int board_y;

    public GomokuGame(Context context, GomokuView view) {
        mContext = context;
        mView = view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void newGame() {
        if (board == null) {
            board = new Board();
        } else {
            board.clearBoard();
        }
        mView.invalidate();
    }

    /**
     * Adds stone when user locates a stone on the board.
     */
    public void addStone() {
        if (board.isCellsAvailable()) {
            String color = turn % 2 == 0 ? "Black" : "White";
            Stone cell = new Stone(stone_x, stone_y, color);
            cell.setBoard_x(board_x);
            cell.setBoard_y(board_y);

            if (board.isCellAvailable(cell)) {
                spawnCell(cell);
            }
        }
    }

    /**
     * Generate new cells
     * @param cell containing location and value
     */
    private void spawnCell(Stone cell) {
        board.insertStone(cell);
    }
}
