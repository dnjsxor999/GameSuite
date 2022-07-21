package com.example.k_dev_master.gomoku;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.k_dev_master.R;


public class GomokuGame extends AppCompatActivity {
    private Context mContext = null;
    private GomokuView mView = null;
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

    public GomokuGame() {
    }

    public GomokuGame(Context context, GomokuView view) {
        mContext = context;
        mView = view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mView = new GomokuView(this);
        setContentView(R.layout.activity_main_gomoku);
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
        if (board.isStonesAvailable()) {
            Stone.Color color = turn % 2 == 0 ? Stone.Color.BLACK : Stone.Color.WHITE;
            System.out.println("sX: " + stone_x + "\nsY: " + stone_y + "\nbX: " + board_x + "\nbY: " + board_y);
            Stone cell = new Stone(stone_x, stone_y, color);

            //만나는 점 가운데로 정렬 필요
            cell.setCoordinateX(board_x);
            cell.setCoordinateY(board_y);

            if (board.isStoneAvailable(cell)) {
                spawnCell(cell);
            }

            if (checkWin(cell)) {
                //TODO: What to do when in
                System.out.println("Win!!!");
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

    private boolean checkWin(Stone stone) {
        return checkWinLU(stone) || checkWinU(stone) || checkWinRU(stone) || checkWinR(stone)
                || checkWinRD(stone) || checkWinD(stone) || checkWinLD(stone) || checkWinL(stone);
    }

    public Context getmContext() {
        return mContext;
    }

    private boolean checkWinLU(Stone stone) {
        int total;
        //왼위확인
        try {
            total = 0;
            for (int i = 0; i < 5; i++) {
                if (board.getStone(stone.getX() - i, stone.getY() - i).getColor() == stone.getColor()) {
                    total++;
                }
            }
            return total == 5;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean checkWinU(Stone stone) {
        int total;
        //위확인
        try {
            total = 0;
            for (int i = 0; i < 5; i++) {
                if (board.getStone(stone.getX(), stone.getY() - i).getColor() == stone.getColor()) {
                    total++;
                }
            }
            return total == 5;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean checkWinRU(Stone stone) {
        int total;
        //오위확인
        try {
            total = 0;
            for (int i = 0; i < 5; i++) {
                if (board.getStone(stone.getX() + i, stone.getY() - i).getColor() == stone.getColor()) {
                    total++;
                }
            }
            return total == 5;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean checkWinR(Stone stone) {
        int total;
        //오확인
        try {
            total = 0;
            for (int i = 0; i < 5; i++) {
                if (board.getStone(stone.getX() + i, stone.getY()).getColor() == stone.getColor()) {
                    total++;
                }
            }
            return total == 5;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean checkWinRD(Stone stone) {
        int total;
        //오밑확인
        try {
            total = 0;
            for (int i = 0; i < 5; i++) {
                if (board.getStone(stone.getX() + i, stone.getY() + i).getColor() == stone.getColor()) {
                    total++;
                }
            }
            return total == 5;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean checkWinD(Stone stone) {
        int total;
        //밑확인
        try {
            total = 0;
            for (int i = 0; i < 5; i++) {
                if (board.getStone(stone.getX(), stone.getY() + i).getColor() == stone.getColor()) {
                    total++;
                }
            }
            return total == 5;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean checkWinLD(Stone stone) {
        int total;
        //왼밑확인
        try {
            total = 0;
            for (int i = 0; i < 5; i++) {
                if (board.getStone(stone.getX() - i, stone.getY() + i).getColor() == stone.getColor()) {
                    total++;
                }
            }
            return total == 5;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }

    private boolean checkWinL(Stone stone) {
        int total;
        //왼확인
        try {
            total = 0;
            for (int i = 0; i < 5; i++) {
                if (board.getStone(stone.getX() - i, stone.getY()).getColor() == stone.getColor()) {
                    total++;
                }
            }
            return total == 5;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        } catch (NullPointerException e) {
            return false;
        }
    }
}
