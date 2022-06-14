package com.example.k_dev_master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 3;
    //private CircleIndicator3 mIndicator;

    public static int gameSelect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**         * 가로 슬라이드 뷰 Fragment         */
        //ViewPager2
        mPager = findViewById(R.id.viewpager);
        //Adapter
        pagerAdapter = new MyAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);
        //ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        /**
         * 이 부분 조정하여 처음 시작하는 이미지 설정.
         * 2000장 생성하였으니 현재위치 1002로 설정하여
         * 좌 우로 슬라이딩 할 수 있게 함. 거의 무한대로
         */
        mPager.setCurrentItem(0);
        //시작 지점
        mPager.setOffscreenPageLimit(3);
        //최대 이미지 수
        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//                mIndicator.animatePageSelected(position%num_page);
//            }
        });

        Button btTeamInfo = (Button) findViewById(R.id.teamInfo); // TeamInfo button
        Button commonStart = findViewById(R.id.commonStart); // GameStart button
        Button quitButton = findViewById(R.id.exit); // exit button

        btTeamInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ShowTeamInfo.class);
                startActivity(intent);
                finish();
            }
        });

        commonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameSelect == 1) {
                    Intent intent = new Intent(getApplicationContext(), LogicGame2048.class);
                    startActivity(intent);
                }

            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }
}