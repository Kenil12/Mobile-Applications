package com.example.timecountdown;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 30000;
    private TextView mTextviewCountdown;
    private FloatingActionButton mButtonStart;
    private FloatingActionButton mButtonreset ;
    private CountDownTimer mCountDownTimer;
    private Button incButton;
    private Button decButton;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextviewCountdown = findViewById(R.id.countdown);
        mButtonStart = findViewById(R.id.startb);
        mButtonreset = findViewById(R.id.resetb);
        FloatingActionButton mButtonPause = findViewById(R.id.pauseb);
        incButton = findViewById(R.id.upward);
        decButton = findViewById(R.id.downward);

        mButtonStart.setOnClickListener(v -> startTimer());
        mButtonPause.setOnClickListener(v -> pauseTimer());
        mButtonreset.setOnClickListener(v -> resetTimer());
        incButton.setOnClickListener(v -> incFunc());

        decButton.setOnClickListener(v -> decFunc());
    }

    private  void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updatecount();
            }

            @Override
            public void onFinish() {
                resetTimer();
            }
        }.start();
        mButtonreset.setVisibility(View.INVISIBLE);
        incButton.setVisibility(View.INVISIBLE);
        decButton.setVisibility(View.INVISIBLE);
        mButtonStart.setVisibility(View.INVISIBLE);
    }

    private  void pauseTimer() {
        mCountDownTimer.cancel();
        mButtonStart.setVisibility(View.VISIBLE);
        mButtonreset.setVisibility(View.VISIBLE);
        incButton.setVisibility(View.VISIBLE);
        decButton.setVisibility(View.VISIBLE);
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        mButtonStart.setVisibility(View.VISIBLE);
        mButtonreset.setVisibility(View.VISIBLE);
        incButton.setVisibility(View.VISIBLE);
        decButton.setVisibility(View.VISIBLE);
        updatecount();
    }

    private void updatecount() {
        int sec = (int) (mTimeLeftInMillis/1000);
        String timeLeftformatted = String.format(Locale.getDefault(), "%02d", sec );
        mTextviewCountdown.setText(timeLeftformatted);
    }
    private void incFunc(){
        mTimeLeftInMillis = mTimeLeftInMillis + 1000;
        if(mTimeLeftInMillis>= 99000){
            mTimeLeftInMillis = 99000;
        }
        updatecount();
    }

    private void decFunc(){
        mTimeLeftInMillis = mTimeLeftInMillis - 1000;
        if(mTimeLeftInMillis<= 5000){
            mTimeLeftInMillis = 5000;
        }
        updatecount();
    }
}