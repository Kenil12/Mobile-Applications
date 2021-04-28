package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class final_display extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_display);
        Button again = findViewById(R.id.Restart);
        TextView display = findViewById(R.id.final_score);
        Intent intent = getIntent();
        String Sc = intent.getStringExtra("score");
        System.out.println(Sc);
        display.setText(Sc);
        again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(final_display.this, go_screen.class));
            }
        });
    }
}