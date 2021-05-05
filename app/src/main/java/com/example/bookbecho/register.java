package com.example.bookbecho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {

    EditText name,emailid,passcode,confirmpassword;
    Button register,gotologin;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        emailid = findViewById(R.id.emailid);
        passcode = findViewById(R.id.passcode);
        confirmpassword = findViewById(R.id.confrimpassword);
        register = findViewById(R.id.register);
        gotologin = findViewById(R.id.gotologin);

        fAuth = FirebaseAuth.getInstance();

        gotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EXTRACTING DATA
                String fullName = name.getText().toString();
                String email = emailid.getText().toString();
                String password = passcode.getText().toString();
                String confpassword = confirmpassword.getText().toString();

                //DATA VALIDATION

                if(fullName.isEmpty())
                {
                    name.setError("Full name is required");
                    return;
                }
                if(email.isEmpty())
                {
                    emailid.setError("email id is required");
                    return;
                }
                if(password.isEmpty())
                {
                    passcode.setError("password is required");
                    return;
                }
                if(confpassword.isEmpty())
                {
                    confirmpassword.setError("confirm password is required");
                    return;
                }
                if(!password.equals(confpassword))
                {
                    confirmpassword.setError("Passwords Do not match");
                    return;
                }

                //DATA IS VALIDATED
                Toast.makeText(register.this, "Data validated", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(),login.class));
                finish();

                fAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user.isEmailVerified()) {

                                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                //finish();
                                Toast.makeText(register.this, "you are verified", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                user.sendEmailVerification();
                                Toast.makeText(register.this, "check mail", Toast.LENGTH_SHORT).show();
                            }
//                        Intent i1 = new Intent(register.this, login.class);
//                        startActivity(i1);

                        }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}