package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout name;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.username);
        Button begin = findViewById(R.id.signInButton);
        begin.setOnClickListener(v -> {
//
//            rootNode = FirebaseDatabase.getInstance();
//            reference = rootNode.getReference("username");
//            String username = name.getEditText().getText().toString();
//            data helper = new data(username);
//
//            reference.child(String.valueOf(name)).setValue(helper);
//            ifUserExists();
            startActivity(new Intent(MainActivity.this, go_screen.class));
        });

    }
        private void ifUserExists(){
        String user = name.getEditText().getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("username");
        Query checkUser = reference.orderByChild("username").equalTo(user);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String usernameFromDb = snapshot.child(user).getValue(String.class);
                    System.out.println(usernameFromDb);
                    if(usernameFromDb.equals(user)){
                        Intent intent = new Intent(MainActivity.this, Quize_main.class);
                        intent.putExtra("username", user);
                        startActivity(intent);
                    }
                    else{
                        data val = new data(user);
                        reference.child(user).setValue(val);
                    }
                }
                else{
                    data val = new data(user);
                    reference.child(user).setValue(val);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}