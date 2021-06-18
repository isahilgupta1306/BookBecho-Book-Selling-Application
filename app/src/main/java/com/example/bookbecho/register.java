package com.example.bookbecho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookbecho.models.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class register extends AppCompatActivity {

    EditText name,emailid,passcode,confirmpassword;
    Button register;
    FirebaseAuth fAuth;
    TextView gotologin;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
//    MaterialButton gotologin;
    AutoCompleteTextView collegename;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        emailid = findViewById(R.id.emailid);
        passcode = findViewById(R.id.passcode);
        confirmpassword = findViewById(R.id.confrimpassword);
        register = findViewById(R.id.register);
        gotologin = findViewById(R.id.gotologin);
        collegename = (AutoCompleteTextView)findViewById(R.id.collegename);

        //For AutoComplete
        String[] colleges = getResources().getStringArray(R.array.colleges);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, colleges);
        collegename.setAdapter(adapter);

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

                        String uuid = fAuth.getCurrentUser().getUid();

                        UserModel userModel = new UserModel(fullName,uuid);
                        DocumentReference documentReference = firebaseFirestore.collection("Users").document(uuid);
                        documentReference.set(userModel);
//                        firebaseDatabase.getReference().child("Users").push().setValue(userModel);

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