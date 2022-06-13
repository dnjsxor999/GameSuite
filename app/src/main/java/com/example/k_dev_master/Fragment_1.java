package com.example.k_dev_master;
import static com.example.k_dev_master.MainActivity.gameSelect;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class Fragment_1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.game_2048, container, false);
        gameSelect = 1;
        return rootView;
    }

}
