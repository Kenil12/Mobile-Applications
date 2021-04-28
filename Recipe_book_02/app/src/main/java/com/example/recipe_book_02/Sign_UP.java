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

import java.util.HashMap;

public class Sign_UP extends AppCompatActivity {
    Button signup;
    EditText name;
    EditText phone;
    EditText email;
    EditText pass;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__u_p);
        signup = findViewById(R.id.signup);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("user");
        auth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname, mail, phon_num, password;
                fullname = name.getText().toString().trim();
                mail = email.getText().toString().trim();
                password = pass.getText().toString().trim();
                phon_num = phone.getText().toString().trim();

                if (isValid(fullname, mail, phon_num, password)){
                    auth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String UserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                HashMap hasmap1 = new HashMap();
                                hasmap1.put("name", fullname);
                                hasmap1.put("phone", phon_num);
                                hasmap1.put("email", mail);
                                hasmap1.put("password", password);

                                reference.child(UserId).setValue(hasmap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(Sign_UP.this, "Varification Sent, Please verify your email", Toast.LENGTH_LONG).show();
                                                    startActivity(new Intent(Sign_UP.this, menu.class));
                                                }
                                                else {
                                                    Toast.makeText(Sign_UP.this, task.getException().toString(), Toast.LENGTH_LONG).show();

                                                }
                                            }
                                        });

                                    }
                                });
                            }
                        }
                    });

                }
            }
        });

       }

    public boolean isValid(String _name, String _email, String _phone, String _pass){

        String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        boolean isValid=false,isValidname=false,isValidemail=false,isValidpassword=false,isValidmobilenum=false;
        if(_name.equals("") && _name.matches("[A-Z][a-z]*")){

            name.setError("Enter Name");
        }else{
            isValidname = true;
        }
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
            pass.setError("Enter Password");
        }else{
            if(_pass.length()<8){
                pass.setError("Password is Weak");
            }else{
                isValidpassword = true;
            }
        }

        if(_phone.equals("")){
            phone.setError("Mobile Number Is Required");
        }else{
            if(_phone.length()<10 && _phone.matches("\\d+(?:\\.\\d+)?")){
                phone.setError("Invalid Mobile Number");
            }else{
                isValidmobilenum = true;
            }
        }

        isValid = (isValidpassword  && isValidemail && isValidmobilenum && isValidname ) ? true : false;
        return isValid;
    }

}