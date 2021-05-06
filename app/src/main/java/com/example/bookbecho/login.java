package com.example.bookbecho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    Button loginButton;
    EditText username,password;
    FirebaseAuth firebaseAuth;
    TextView forgot , createacc;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();


        createacc = findViewById(R.id.createacc);
        createacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),register.class));
            }
        });

        username = findViewById(R.id.emailaddress);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        forgot = findViewById(R.id.forgot);



        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),forgotPassword.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // extraction and validation
                String var1 = username.getText().toString();
                String var2 = password.getText().toString();
                // extraction and validation done
                if(var1.isEmpty()) {
                    username.setError("username invalid");
                    return;
                }

                if(var2.isEmpty()) {
                    password.setError(("password invalid"));
                    return;
                }

                // user login

                firebaseAuth.signInWithEmailAndPassword(var1,var2).addOnSuccessListener(new OnSuccessListener<AuthResult>() {

                    @Override
                    public void onSuccess(AuthResult authResult) {
//                        if(var1.isEmpty())
//                            username.setError("username invalid");
//                        if(var2.isEmpty())
//                            password.setError("password invalid");
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user.isEmailVerified()) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        }
                        else{
                            user.sendEmailVerification();
                            Toast.makeText(login.this, "check you email", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }
}