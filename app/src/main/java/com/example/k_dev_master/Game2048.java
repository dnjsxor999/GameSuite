package com.example.k_dev_master;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Game2048 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_2048);

        ImageButton openMenu = findViewById(R.id.ListViewBtn2048);

        openMenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Game2048.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.item1:
                                startActivity(new Intent());

                            case R.id.item2:
                                startActivity(new Intent());

                            case R.id.item3:
                                startActivity(new Intent());

                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });
    }

}
