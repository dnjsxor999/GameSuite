package com.example.k_dev_master;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

public class MemoryGame extends AppCompatActivity {

    private MainViewMemory view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MainViewMemory(this);
        setContentView(view);
        view.invalidate();
    }
}
