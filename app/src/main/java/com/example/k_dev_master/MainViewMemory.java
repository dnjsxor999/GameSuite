package com.example.k_dev_master;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MainViewMemory extends View {

    public MainGameMemory game;
    private Bitmap background = null;
    private final Paint paint = new Paint();
    private int cardSizeX = 170;
    private int cardSizeY = 250;
    private int gridWidth = 50;
    private int startingX;
    private int endingX;
    private int startingY;
    private int endingY;
    private final BitmapDrawable[] bitmapCells = new BitmapDrawable[14];


    public MainViewMemory(Context context) {
        super(context);
        init(null);
        Resources resources = context.getResources();
        this.setBackground(resources.getDrawable(R.drawable.cardgame_background));
        game = new MainGameMemory(context, this);

        game.newGame();
    }

    public MainViewMemory(Context context, AttributeSet attrs) {
        super(context, attrs);
        Resources resources = context.getResources();
        this.setBackground(resources.getDrawable(R.drawable.cardgame_background));
        game = new MainGameMemory(context, this);
        game.newGame();
        init(attrs);
    }

    public MainViewMemory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Resources resources = context.getResources();
        this.setBackground(resources.getDrawable(R.drawable.cardgame_background));
        game = new MainGameMemory(context, this);

        game.newGame();
        init(attrs);
    }


    private void init(@Nullable AttributeSet set) {
    }
    @Override
    public void onDraw(Canvas canvas) {
        //Reset the transparency of the screen
        super.onDraw(canvas);
        canvas.drawBitmap(background, 0, 0, paint);
        drawTime(canvas);
        drawStage(canvas);
        drawCards(canvas);

        invalidate();
    }

    private void setDraw(Canvas canvas, Drawable draw, int startingX, int startingY, int endingX, int endingY) {
        draw.setBounds(startingX, startingY, endingX, endingY);
        draw.draw(canvas);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        gridWidth = 10;
        int screenMiddleX = w / 2;
        int screenMiddleY = h / 2;
        int boardMiddleY = screenMiddleY + cardSizeY;
        startingX = screenMiddleX - (cardSizeX + gridWidth) * 2 - gridWidth / 2;
        endingX = screenMiddleX + (cardSizeX + gridWidth) * 2 + gridWidth / 2;
        startingY = boardMiddleY - (cardSizeY + gridWidth) * 2 - gridWidth / 2;
        endingY =  boardMiddleY + (cardSizeY + gridWidth) * 2 + gridWidth / 2;
        createBitmapCells();
        background = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(background);
        drawBackgroundGrid(canvas);
    }

    private void drawBackgroundGrid(Canvas canvas) {
        Resources resources = getResources();
        Drawable backgroundCell = resources.getDrawable(R.drawable.card_blank);
        // Outputting the game grid
        for (int xx = 0; xx < 4; xx++) {
            for (int yy = 0; yy < 4; yy++) {
                int sX = startingX + gridWidth + (cardSizeX + gridWidth) * xx;
                int eX = sX + cardSizeX;
                int sY = startingY + gridWidth + (cardSizeY + gridWidth) * yy;
                int eY = sY + cardSizeY;

                setDraw(canvas, backgroundCell, sX, sY, eX, eY);
            }
        }
    }
    public void drawStage(Canvas canvas) {
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);

        paint.setColor(getResources().getColor(R.color.white));
        canvas.drawText(String.valueOf(game.stage.getStageLevel()), 300,200, paint);
    }

    public void drawTime(Canvas canvas) {
        paint.setTextSize(50);
        paint.setTextAlign(Paint.Align.CENTER);

        paint.setColor(getResources().getColor(R.color.white));
        canvas.drawText(String.valueOf(game.stage.getStageTime(game.stage.getStageLevel()) / 1000), 500,400, paint);
    }
    private void drawCards(Canvas canvas) {
        for (int xx = 0; xx < 4; xx++) {
            for (int yy = 0; yy < 4; yy++) {

                int sX = startingX + gridWidth + (cardSizeX + gridWidth) * xx; // starting x for each cell
                int eX = sX + cardSizeX;
                int sY = startingY + gridWidth + (cardSizeY + gridWidth) * yy;
                int eY = sY + cardSizeY;

                Card currCard = game.stage.getCard(xx, yy);
                if (currCard != null) {
                    if (currCard.isHidden()) {
                        bitmapCells[0].setBounds(sX, sY, eX, eY);
                        bitmapCells[0].draw(canvas);
                    } else {
                        int value = currCard.getValue();
                        bitmapCells[value].setBounds(sX, sY, eX, eY);
                        bitmapCells[value].draw(canvas);
                    }
                }
            }
        }
    }

    private void createBitmapCells() {
        Resources resources = getResources();
        int[] cardImages = getCardImage();
        for (int xx = 0; xx < bitmapCells.length; xx++) {
            Bitmap cardBitmap = Bitmap.createBitmap(cardSizeX, cardSizeY, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(cardBitmap);
            setDraw(canvas, resources.getDrawable(cardImages[xx]), 0, 0, cardSizeX, cardSizeY);
            bitmapCells[xx] = new BitmapDrawable(resources, cardBitmap);
        }
    }


    private int[] getCardImage() {
        int[] cardImages = new int[14];
        cardImages[0] = R.drawable.card_blank;
        cardImages[1] = R.drawable.card_clubs1;
        cardImages[2] = R.drawable.card_clubs2;
        cardImages[3] = R.drawable.card_clubs3;
        cardImages[4] = R.drawable.card_clubs4;
        cardImages[5] = R.drawable.card_clubs5;
        cardImages[6] = R.drawable.card_clubs6;
        cardImages[7] = R.drawable.card_clubs7;
        cardImages[8] = R.drawable.card_clubs8;
        cardImages[9] = R.drawable.card_clubs9;
        cardImages[10] = R.drawable.card_clubs10;
        cardImages[11] = R.drawable.card_clubs11;
        cardImages[12] = R.drawable.card_clubs12;
        cardImages[13] = R.drawable.card_clubs13;
        return cardImages;
    }


}