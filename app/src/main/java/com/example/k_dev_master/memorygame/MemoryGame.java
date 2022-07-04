package com.example.k_dev_master.memorygame;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.k_dev_master.*;
import com.example.k_dev_master.R;
import com.example.k_dev_master.databinding.ActivityMemorygameBinding;

import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class MemoryGame extends AppCompatActivity {

    private final long TIME_DISPLAY_STAGE1 = 4000;
    private final long TIME_DISPLAY_STAGE2 = 8000;
    private final long TIME_DISPLAY_STAGE3 = 5000;
    ActivityMemorygameBinding binding;
    private int stageLevel = 0;
    Vector<Card> cards;
    Timer timer;
    private long recordTime = 0;
    private long currTimer = 0;
    private int gameState;
    private static final int GAME_ONGOING = 0;
    private static final int GAME_DONE = 1;
    Vector<Integer> selectedPos;
    // 골라 놓은 카드들 벡터로 저장

    // count 100ms
    TimerTask tt;
    TextView timerText;
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

        gameState = GAME_ONGOING;
        recordTime = 0;
        currTimer = 0;
        stageLevel = 1;
        setTask();
        selectedPos = new Vector<>();
        //Timer
        timerText = (TextView) findViewById(R.id.timerTxtView);

        //비교할 후보들 저장

        addCards();
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
                handler.postDelayed(MemoryGame.this::start, 100); // timer need to be implemented
                binding.cardLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        // popup menu button implementation
        ImageButton openMenu = findViewById(R.id.ListViewBtnMemory);
        openMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MemoryGame.this, view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_2048, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.pop_2048:
                                startActivity(new Intent(getApplicationContext(), MemoryGame.class));
                                break;
                            case R.id.pop_2048_inst:
                                LayoutInflater inflater = (LayoutInflater)
                                        getSystemService(LAYOUT_INFLATER_SERVICE);
                                View popupView = inflater.inflate(R.layout.popup_instruction_memory, null);

                                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                                boolean focusable = true; // lets taps outside the popup also dismiss it
                                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                                // show the popup window
                                // which view you pass in doesn't matter, it is only used for the window tolken
                                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                                // dismiss the popup window when touched
                                popupView.setOnTouchListener(new View.OnTouchListener() {
                                    @Override
                                    public boolean onTouch(View v, MotionEvent event) {
                                        popupWindow.dismiss();
                                        return true;
                                    }
                                });
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
                                .rotationYBy(360)
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
                                                                if (display.equals(display2)) {
                                                                    Toast.makeText(MemoryGame.this, "Correct!", Toast.LENGTH_SHORT).show();
                                                                    adapter.update(pos1, 2);
                                                                    adapter.update(pos2, 2);
                                                                    Log.e("Matched!", display);
                                                                    if (matchedAll(cards)) {
                                                                        Log.e("In if statement", "yes!");
                                                                        timer.cancel();
                                                                        stageUp();
                                                                    }
                                                                } else { // 틀림
                                                                    adapter.update(pos1, 0);
                                                                    adapter.update(pos2, 0);
                                                                    Log.e("UnMatched..", display);
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


    private boolean matchedAll(Vector<Card> cards) {
        for (Card card : cards) {
//            Log.e("passed the card", card.getTag());
//            Log.e("check value", "" + card.getCheck());
            if (card.getCheck() != 2) { // if a card is matched, the check value is 2
//                Log.e("All matched?", "false");
                return false;
            }
        }
//        Log.e("All matched?", "true");
        return true;
    }

    private void regame() {
        for (Card card : cards) {
            card.setCheck(0);
        }
        Collections.shuffle(cards);
    }

    private void start() {
        new CountDownTimer(getTimeDisplay(), 1000) {
            public void onTick(long millisUntilFinished) {
                binding.timerTxtView.setVisibility(View.VISIBLE);
                timerText.setTextColor(Color.RED);
                timerText.setText("Remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                timerText.setTextColor(Color.BLACK);
            }
        }.start();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            // 정답 보여주기
            if (stageLevel == 1) {
                binding.cardViews.addOnItemTouchListener(onItemTouchListener);
            }
            adapter.setStartAnimate(true);
            //첫시작할때 모두 뒤집기
        }, getTimeDisplay()); // show answer for 10 secs
        handler.postDelayed(this::timer, getTimeDisplay());
    }

    private void timer() {
        binding.timerTxtView.setVisibility(View.VISIBLE);
        //timer implement needed
        //stage 1 2 3 will be initiated in here
        timer = new Timer();
        timer.schedule(tt, 0,100); // count 100 each 10ms
    }

    private void setTask() { // set TimerTake again 만약 다시 세팅을 안하면 팅김
        tt = new TimerTask() {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Log.e("Counting Time", String.valueOf(currTimer));
                        currTimer += 1;
                        timerText.setText(getTimerText());
                    }
                });
            }
        };
    }

    public void stageUp() {
        binding.timerTxtView.setVisibility(View.GONE);
        if (stageLevel == 1) {
            stageLevel = 2;
            Log.e("Curr time", String.valueOf(currTimer));
            recordTime += currTimer;
        } else if (stageLevel == 2) {
            stageLevel = 3;
            Log.e("Curr time", String.valueOf(currTimer));
            recordTime += currTimer;

        } else if (stageLevel == 3) {
            gameState = GAME_DONE;
            recordTime += currTimer;
            currTimer = 0;
            Log.e("Total recorded time", String.valueOf(recordTime));
            return ;
        }
        currTimer = 0;
        setTask();
        adapter.setStartAnimate(false);

        regame();

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
                handler.postDelayed(MemoryGame.this::start, 2000); // timer need to be implemented
                binding.cardLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    private void addCards() {
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
        boolean shuffle = false;
        if (shuffle) {
            Collections.shuffle(cards);
        }
    }

    private long getTimeDisplay() {
        if (stageLevel == 1) {
            return TIME_DISPLAY_STAGE1;
        } else if (stageLevel == 2) {
            return TIME_DISPLAY_STAGE2;
        } else if (stageLevel == 3) {
            return TIME_DISPLAY_STAGE3;
        } else {
            return 0;
        }
    }

    private String getTimerText()
    {
        int rounded = (int) Math.round(currTimer);

        long milliseconds = currTimer % 10;
        int seconds = rounded / 10;

        return formatTime(milliseconds, seconds);
    }

    private String formatTime(long milliseconds, int seconds)
    {
        return String.format("%02d",seconds) + "." + String.format("%01d",milliseconds);
    }
}
