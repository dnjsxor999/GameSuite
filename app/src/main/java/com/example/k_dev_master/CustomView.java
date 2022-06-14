package com.example.k_dev_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;

public class CustomView extends View {

    private Drawable mCustomImage;

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mCustomImage = ResourcesCompat.getDrawable(getResources(), R.drawable.tile_2, null);

    }

    protected void onDraw(Canvas canvas) {
        Rect imageBounds = canvas.getClipBounds();
        imageBounds.set(50,50,200,200);// Adjust this for where you want it

        mCustomImage.setBounds(imageBounds);
        mCustomImage.draw(canvas);
    }

}