package com.example.recipe_book_02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button sing_up;
    Button sing_in;
    EditText email;
    EditText password;
    String _mail, _pass;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sing_up = findViewById(R.id.signup);
        sing_in = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        sing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Sign_UP.class));
            }
        });

        sing_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mail = email.getText().toString().trim();
                _pass = password.getText().toString().trim();
                if (isValid(_mail, _pass)){
                    auth.signInWithEmailAndPassword(_mail, _pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if( task.isSuccessful()) {
                                if (auth.getCurrentUser().isEmailVerified()){
                                    Toast.makeText(MainActivity.this, "Login Succesfull", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(MainActivity.this, menu.class));
                                  }
                                else {
                                    Toast.makeText(MainActivity.this, "User Error", Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Mail Verifiation Faild, Pleace create the account", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

            }
        });

    }


    public boolean isValid(String _email, String _pass){

        String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        boolean isValid=false, isValidemail=false, isValidpassword=false;

        if(_email.equals("")){

            email.setError("Enter Email");
        }else{
            if(_email.matches(emailpattern)){
                isValidemail = true;
            }else{
                email.setError("Enter Valid Email Id");
            }
        }
        if(_pass.equals("")){
            password.setError("Enter Password");
        }else{
            if(_pass.length()<8){
                password.setError("Password is Weak");
            }else{
                isValidpassword = true;
            }
        }



        isValid = (isValidpassword  && isValidemail ) ? true : false;
        return isValid;
    }

}