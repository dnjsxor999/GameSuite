package com.example.k_dev_master;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

public class LogicGame2048 extends AppCompatActivity {
    // 2048 Game
    private MainView2048 view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MainView2048(this);
        setContentView(R.layout.activity_main_2048game);
        ImageButton openMenu = findViewById(R.id.ListViewBtn2048);
        view.invalidate();
        openMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(LogicGame2048.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_2048, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.pop_2048:

                                startActivity(new Intent(getApplicationContext(), LogicGame2048.class));
                                break;
                            case R.id.pop_2048_inst:
                                break;
                            case R.id.pop_2048_exit:
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                                break;
                            default:
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
//        findViewById(R.layout.activity_main_2048game).setOnTouchListener(this);
    }
//    protected void newGame2048() {
//        view.game = new MainGame2048(this, new MainView2048(this));
//    }



//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_MENU) {
//            //Do nothing
//            return true;
//        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
//            view.game.move(2);
//            return true;
//        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
//            view.game.move(0);
//            return true;
//        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
//            view.game.move(3);
//            return true;
//        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
//            view.game.move(1);
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    //    @Override
    //    public void onBackPressed() {
    //        Toast.makeText(this, "Back button pressed.", Toast.LENGTH_SHORT).show();
    //        super.onBackPressed();
    //    }
}
