package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class Quize_main extends AppCompatActivity {

    Random rand = new Random();
    private Button play, stop, a, b, c, d; // here a, b, c, d are options
    private TextView show_time, show_result, show_question, show_score;
    private CountDownTimer set_time;
    int score_counter = 0, x, y, correct, select,  counter = 25000, totalque = 0;
    int opt_1, opt_2, opt_3, opt_4, i=0;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.secs10:
                counter = 10000;
                show_time.setText("10");
                return true;
            case R.id.secs15:
                counter = 15000;
                show_time.setText("15");

                return true;
            case R.id.secs20:
                counter = 20000;
                show_time.setText("20");
                return true;
            case R.id.secs25:
                counter = 25000;
                show_time.setText("25");
                return true;
            case R.id.pastScores:
                gotoScores();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quize_main);
        show_time = findViewById(R.id.timer);
        show_score = findViewById(R.id.displayscore);
        show_question = findViewById(R.id.Question);
        show_result = findViewById(R.id.result);
        stop = findViewById(R.id.gotoScores);
        play = findViewById(R.id.play);
        a = findViewById(R.id.opta);
        b = findViewById(R.id.optb);
        c = findViewById(R.id.optc);
        d = findViewById(R.id.optd);
        play.setVisibility(View.VISIBLE);
        show_result.setVisibility(View.INVISIBLE);
        stop.setVisibility(View.INVISIBLE);
        a.setEnabled(false);
        b.setEnabled(false);
        c.setEnabled(false);
        d.setEnabled(false);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop.setVisibility(View.VISIBLE);
                play.setVisibility(View.INVISIBLE);
                start_time();
            }
        });

        question_generator();

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opt_1 == correct){
                    show_result.setText("Correct!!");
                    score_counter++;
                    totalque++;
                }
                else {
                    show_result.setText("Wron Answer!!");
                    totalque++;
                }
                show_score.setText("" + score_counter + "/"+ totalque);
                show_result.setVisibility(View.VISIBLE);
                question_generator();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoScores();
            }
        });


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opt_2 == correct){
                    show_result.setText("Correct!!");
                    score_counter++;
                    totalque++;
                }
                else {
                    show_result.setText("Wron Answer!!");
                    totalque++;
                }
                show_score.setText("" + score_counter + "/"+ totalque);
                show_result.setVisibility(View.VISIBLE);
                question_generator();
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opt_3 == correct){
                    show_result.setText("Correct!!");
                    score_counter++;
                    totalque++;
                }
                else {
                    show_result.setText("Wron Answer!!");
                    totalque++;
                }
                show_score.setText("" + score_counter + "/"+ totalque);
                show_result.setVisibility(View.VISIBLE);
                question_generator();
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opt_4 == correct){
                    show_result.setText("Correct!!");
                    score_counter++;
                    totalque++;
                }
                else {
                    show_result.setText("Wron Answer!!");
                    totalque++;
                }
                show_score.setText("" + score_counter + "/"+ totalque);
                show_result.setVisibility(View.VISIBLE);
                question_generator();
            }
        });

    }

    void start_time(){
        if (i == 0) {
            show_question.setVisibility(View.VISIBLE);
            a.setEnabled(true);
            b.setEnabled(true);
            c.setEnabled(true);
            d.setEnabled(true);
            i = i + 1;
            set_time = new CountDownTimer (counter, 1000) {
                public void onTick(long millisUntilFinished) {
                    show_time.setText("" + millisUntilFinished / 1000);
                }
                public void onFinish() {
                    a.setEnabled(false);
                    b.setEnabled(false);
                    c.setEnabled(false);
                    d.setEnabled(false);
                    show_result.setVisibility(View.VISIBLE);
                    show_result.setText("Time Finished");
                    stop.setText("scores");

                }
            }.start();
        }
        else if(i == 1){
            i=0;
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    void question_generator(){
        x = rand.nextInt((100-1) + 1) + 1;
        y = rand.nextInt((100-1) + 1) + 1;
        correct = x+y;
        select = rand.nextInt(3);
        opt_1 = rand.nextInt((100-1) + 1) + 1;
        opt_2 = rand.nextInt((100-1) + 1) + 1;
        opt_3 = rand.nextInt((100-1) + 1) + 1;
        opt_4 = rand.nextInt((100-1) + 1) + 1;
        if(select== 0){
            opt_1 = correct;
        }
        else if(select== 1){
            opt_2 = correct;
        }
        else if(select== 2){
            opt_3 = correct;
        }
        else{
            opt_4 = correct;
        }
        show_question.setText(x + " + " + y);
        a.setText(""+ opt_1);
        b.setText(""+ opt_2);
        c.setText(""+ opt_3);
        d.setText(""+ opt_4);
    }

    void gotoScores(){
        Intent intent = new Intent(Quize_main.this, final_display.class);
        intent.putExtra("score", ""+ score_counter + "/" + totalque);
        startActivity(intent);
    }
}