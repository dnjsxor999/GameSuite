package com.example.k_dev_master;


import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
//import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.Vector;

import com.example.k_dev_master.R;
import com.example.k_dev_master.databinding.ActivityMemorygameBinding;

public class MemoryGame extends AppCompatActivity {

    ActivityMemorygameBinding binding;

    Vector<Card> cards;

    Vector<Integer> selectedPos;
    // 골라 놓은 카드들 벡터로 저장

    MemoryGameAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_memorygame);
//        binding.restartBtn.setOnClickListener(view -> recreate());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        binding.cardViews.setLayoutManager(gridLayoutManager);
        adapter = new MemoryGameAdapter(this);
        binding.cardViews.setAdapter(adapter);


        selectedPos = new Vector<>();
        //비교할 후보들 저장
        cards = new Vector<>();
        cards.add(new Card(R.drawable.card_clubs1, "1"));
        cards.add(new Card(R.drawable.card_clubs1, "1"));
        cards.add(new Card(R.drawable.card_clubs2, "2"));
        cards.add(new Card(R.drawable.card_clubs2, "2"));
        cards.add(new Card(R.drawable.card_clubs3, "3"));
        cards.add(new Card(R.drawable.card_clubs3, "3"));
        cards.add(new Card(R.drawable.card_clubs4, "4"));
        cards.add(new Card(R.drawable.card_clubs4, "4"));
        cards.add(new Card(R.drawable.card_clubs5, "5"));
        cards.add(new Card(R.drawable.card_clubs5, "5"));
        cards.add(new Card(R.drawable.card_clubs6, "6"));
        cards.add(new Card(R.drawable.card_clubs6, "6"));
        cards.add(new Card(R.drawable.card_clubs7, "7"));
        cards.add(new Card(R.drawable.card_clubs7, "7"));
        cards.add(new Card(R.drawable.card_clubs8, "8"));
        cards.add(new Card(R.drawable.card_clubs8, "8"));
        Collections.shuffle(cards);
        //섞기
        adapter.setUpPicture(cards);

        binding.cardLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width = binding.cardViews.getWidth() / 4 - 10;
                int height = binding.cardViews.getHeight() / 4 - 10;
                Log.e("width", String.valueOf(width));
                Log.e("height", String.valueOf(height));
                adapter.setLength(width, height);
                // set length
                adapter.notifyDataSetChanged();
                // recycleView alert
                Handler handler = new Handler();
                handler.postDelayed(MemoryGame.this::start, 1000); // timer need to be implemented
                binding.cardLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

    }


    RecyclerView.OnItemTouchListener onItemTouchListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent evt) {
            int action = evt.getAction();
            switch (action) {
                case MotionEvent.ACTION_UP:
                    View child = recyclerView.findChildViewUnder(evt.getX(), evt.getY());
//                    assert child != null;
                    int pos = recyclerView.getChildAdapterPosition(child);
                    // get position of touched and its index of vector
                    Log.e("pos", String.valueOf(pos));
                    View txtView = child.findViewById(R.id.cardTxtView);
                    // 하나의 카드
                    // pictureTxtview 객체 생성
                    int check = cards.get(pos).getCheck();
                    // 그 카드객체 (index of vector)의 check 가져오기
                    if(check == 1) { // 뒤집혔다면, 눌려졌다면,
                        txtView.animate()
                                .rotationYBy(180)
                                .rotationY(90)
                                .setDuration(200) // 카드 보여주는 시간
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        txtView.animate()
                                                .rotationYBy(90)
                                                .rotationY(0)
                                                .setDuration(200)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationStart(Animator animation) {
                                                        adapter.setImage(pos, cards.get(pos).getDisplay());
                                                    }

                                                    /**
                                                     * game logic is here
                                                     * @param animation
                                                     */
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        selectedPos.add(pos);
                                                        // 고른 카드 selectedPos vector에 add
                                                        if (selectedPos.size() == 2) {
                                                            // selectedPos vector 카드가 두장 쌓이면 비교 시작
                                                            int pos1 = selectedPos.get(0);
                                                            int pos2 = selectedPos.get(1);
                                                            if(pos1 != pos2) { // 맞추려면 그 포지션이 달라야함
                                                                String display = cards.get(pos1).getTag();
                                                                String display2 = cards.get(pos2).getTag();
                                                                Log.e("display1", display);
                                                                Log.e("display2", display2);
                                                                if (display.equals(display2)) { // 맞춤 -> check == 2
                                                                    Toast.makeText(MemoryGame.this, "Correct!", Toast.LENGTH_SHORT).show();
                                                                    adapter.update(pos1, 2);
                                                                    adapter.update(pos2, 2);
                                                                } else { // 틀림
                                                                    adapter.update(pos1, 0);
                                                                    adapter.update(pos2, 0);
                                                                }
                                                            } else { // 포지션 같으므로 pos1의 카드 check 초기화 (선택안한상태로 되돌림)
                                                                adapter.update(pos1, 0);
                                                            }
                                                            selectedPos.removeAllElements(); // selectedPos vector 초기화
                                                            selectedPos.clear();
                                                        }
                                                    }

                                                })
                                                .start();
                                    }
                                })
                                .start();
                    }
                    break;
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            //no 구현
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {
            //no 구현
        }
    };

    private void timer() {
        binding.timerTxtView.setVisibility(View.VISIBLE);
        //timer implement needed
        //stage 1 2 3 will be initiated in here
    }

    private void start() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            // 정답 보여주기
            binding.cardViews.addOnItemTouchListener(onItemTouchListener);
            adapter.setStartAnimate(true);
            //첫시작할때 모두 뒤집기
        }, 1000);
        handler.postDelayed(this::timer, 1000);
    }
}
