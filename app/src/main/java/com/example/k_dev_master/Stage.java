package com.example.k_dev_master;

public class Stage {
    public Card[][] field;
    public Card[][] blank;
    private int stage_int;
    private final long DISPLAYTIME_STAGE1 = 20000;
    private final long DISPLAYTIME_STAGE2 = 10000;
    private final long DISPLAYTIME_STAGE3 = 5000;

    public Stage(int sizeX, int sizeY, int stage_int) {
        field = new Card[sizeX][sizeY];
        blank = new Card[sizeX][sizeY];
        clearCards(field);
        clearCards(blank);
    }

    public Card getCard(Card card) {
        if (card != null) {
            return field[card.getX()][card.getY()];
        } else {
            return null;
        }
    }

    public Card getCard(int x, int y) {
        return field[x][y];
    }

    public void clearCards(Card[][] cards) {
        for (int xx = 0; xx < 4; xx++) {
            for (int yy = 0; yy < 4; yy++) {
                cards[xx][yy] = null;
            }
        }
    }

    public long getStageTime(int stageLevel) {
        if (stageLevel == 1) {
            return DISPLAYTIME_STAGE1;
        } else if (stageLevel == 2) {
            return DISPLAYTIME_STAGE2;
        } else if (stageLevel == 3) {
            return DISPLAYTIME_STAGE3;
        }
        return 0;
    }
}
