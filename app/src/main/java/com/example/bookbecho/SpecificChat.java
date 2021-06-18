package com.example.bookbecho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookbecho.adapters.MessagesAdapter;
import com.example.bookbecho.models.Messages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SpecificChat extends AppCompatActivity {

    EditText getmessage;
    ImageButton sendButton;
    CardView sendmessageCardView;
    androidx.appcompat.widget.Toolbar mtoolbarofspecificchat;
    TextView nameofspecificuser;

    private String enteredmessage;
    Intent intent;
    String mrecievername, sendername, recieveruid, senderuid;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    String senderroom, recieverroom;
    ImageButton mBackbutton;

    RecyclerView messagerecyclerview;

    String currentTime;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    MessagesAdapter messagesAdapter;
    ArrayList<Messages> messagesArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_chat);

        getmessage = findViewById(R.id.getmessage);
        sendmessageCardView = findViewById(R.id.cardViewofspecificuser);
        sendButton = findViewById(R.id.sendmessagebutton);
        mtoolbarofspecificchat = findViewById(R.id.toolbarofspecificchat);
        nameofspecificuser = findViewById(R.id.profiletext);
        mBackbutton = findViewById(R.id.backbuttonofspecificchat);
        intent = getIntent();

        messagesArrayList = new ArrayList<>();
        messagerecyclerview = findViewById(R.id.recyclerViewchat);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        messagerecyclerview.setLayoutManager(linearLayoutManager);
        messagesAdapter = new MessagesAdapter(SpecificChat.this, messagesArrayList);
        messagerecyclerview.setAdapter(messagesAdapter);

        setSupportActionBar(mtoolbarofspecificchat);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm a");

        senderuid = firebaseAuth.getUid();
        recieveruid = intent.getStringExtra("receiveruid");
        mrecievername = intent.getStringExtra("username");
        senderroom = senderuid+recieveruid;
        recieverroom= recieveruid+senderuid;


        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Chats").child(senderroom).child("messages");
        messagesAdapter = new MessagesAdapter(SpecificChat.this, messagesArrayList);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    Messages messages = snapshot1.getValue(Messages.class);
                    messagesArrayList.add(messages);
                }
                messagesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });



        mBackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nameofspecificuser.setText(mrecievername);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enteredmessage=getmessage.getText().toString();
                if(enteredmessage.isEmpty()){
                    Toast.makeText(SpecificChat.this, "Write Something", Toast.LENGTH_SHORT).show();
                } else {

                    Date date = new Date();
                    currentTime = simpleDateFormat.format(calendar.getTime());
                    Messages messages = new Messages(enteredmessage,firebaseAuth.getUid(), date.getTime(), currentTime);
                    firebaseDatabase= FirebaseDatabase.getInstance();
                    firebaseDatabase.getReference().child("Chats").child(senderroom).child("messages").push().setValue(messages).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            firebaseDatabase.getReference().child("Chats").child(recieverroom).child("messages").push().setValue(messages);
                        }
                    });

                }

            }
        });

        getmessage.setText(null);

    }

    @Override
    public void onStart() {
        super.onStart();
        messagesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(messagesAdapter!=null){
            messagesAdapter.notifyDataSetChanged();
        }
    }

}