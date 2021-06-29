package com.example.bookbecho;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.bookbecho.models.productDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bookbecho.databinding.ActivityProductDetailsBinding;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class productDetails extends AppCompatActivity {

    DatabaseReference database , profileInfo , favRef;
    public String productDescription , productTitle , productPrice , productImageUrl , soldStatus;
    MaterialTextView description , price , title , college , student;
    MaterialButton addToFav , buyNow , chatWithSeller;
    ImageView productImage;
    FirebaseFirestore db;
    public String productOwner;
    public String studentName;
    public String collegeName;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_product_details);
        String uid = FirebaseAuth.getInstance().getUid();
        db = FirebaseFirestore.getInstance();
        database = FirebaseDatabase.getInstance().getReference().child("Products");
        profileInfo = FirebaseDatabase.getInstance().getReference().child("Users");
        favRef = FirebaseDatabase.getInstance().getReference().child("Favorites").child(uid);
        //Hooks
        title = (MaterialTextView)findViewById(R.id.productTitle);
        price = (MaterialTextView)findViewById(R.id.productPrice);
        description = (MaterialTextView)findViewById(R.id.productDescription);
        college = (MaterialTextView)findViewById(R.id.collegeName);
        student = (MaterialTextView)findViewById(R.id.studentName);
        addToFav = (MaterialButton)findViewById(R.id.addtoCart);
        buyNow = (MaterialButton)findViewById(R.id.buyButton);
        chatWithSeller = (MaterialButton)findViewById(R.id.chatwithuser);
        productImage = (ImageView)findViewById(R.id.productImage);
        //Our key
        String key = getIntent().getStringExtra("key");
        String signal = getIntent().getStringExtra("signal");
        Log.d("intentCheck" , signal);

        if(signal.equals("1")){
            setDataFav(key);
            Log.d("intentCheck" , "signal 1");
        }else if(signal.equals("0")) {
            setData(key);
            Log.d("intentCheck" , "signal 0");
        }



        chatWithSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SpecificChat.class);
                DocumentReference docRef = db.collection("Users").document(productOwner);
                docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                       @Override
                       public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                           if (task.isSuccessful()) {
                               DocumentSnapshot document = task.getResult();
                               if (document.exists()) {
                                   studentName = document.getString("username");
                                   Log.d("methodCheck" , "text +" + studentName);
                                   intent.putExtra("username", studentName);
                                   intent.putExtra("receiveruid",productOwner);
                                   startActivity(intent);
                               } else {
                                   Log.d("doc" , "document doesnt exists");
                               }
                           } else {
                               Log.d("doc" , "task unsuccesful");
                           }
                       }
                   });

            }
        });


        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog(key);
            }
        });

        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAddToFav(key);
            }

        });




    }
    private void setDataFav(String key){
        favRef.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    productDescription = snapshot.child("description").getValue().toString();
                    productImageUrl = snapshot.child("photo").getValue().toString();
                    productTitle = snapshot.child("title").getValue().toString();
                    productPrice = snapshot.child("price").getValue().toString();
                    productOwner = snapshot.child("user").getValue().toString();

                    setDatainUI(productOwner);
                    //Setting the values to widget
                    title.setText(productTitle);
                    description.setText(productDescription);
                    price.setText("₹"+ productPrice);
                    Glide.with(productImage.getContext()).load(productImageUrl).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    private void alertDialog(String key) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setIcon(R.drawable.ic_shopping_bag);
        dialog.setMessage("Proceed with the order ?");
        dialog.setTitle("Order Confirmation");
        dialog.setPositiveButton("Place Order",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getApplicationContext(),"Order Confirmed",Toast.LENGTH_LONG).show();
                        buynow(key);

                    }
                });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(getApplicationContext(),"cancel is clicked", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private void setData(String key){
        database.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    productDescription = snapshot.child("description").getValue().toString();
                    productImageUrl = snapshot.child("photo").getValue().toString();
                    productTitle = snapshot.child("title").getValue().toString();
                    productPrice = snapshot.child("price").getValue().toString();
                    productOwner = snapshot.child("user").getValue().toString();

                    setDatainUI(productOwner);
                    //Setting the values to widget
                    title.setText(productTitle);
                    description.setText(productDescription);
                    price.setText("₹"+ productPrice);
                    Glide.with(productImage.getContext()).load(productImageUrl).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }
    private void setAddToFav(String key){
        Toast.makeText(getApplicationContext() , "Added To Favorites", Toast.LENGTH_LONG);
       database = FirebaseDatabase.getInstance().getReference();
//        database.child(key).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    productDescription = snapshot.child("description").getValue().toString();
//                    productImageUrl = snapshot.child("photo").getValue().toString();
//                    productTitle = snapshot.child("title").getValue().toString();
//                    productPrice = snapshot.child("price").getValue().toString();
//                    productOwner = snapshot.child("user").getValue().toString();
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull @NotNull DatabaseError error) {
//
//            }
//        });
        String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        productDataModel productModel = new productDataModel(productTitle, productDescription,productPrice, productImageUrl, productOwner, uuid);
        FirebaseDatabase.getInstance().getReference().child("Favorites").child(uuid).push().setValue(productModel);


    }

    private void buynow(String key){
        String uuid = FirebaseAuth.getInstance().getUid();
        productDataModel productModel = new productDataModel(productTitle, productDescription,productPrice, productImageUrl, productOwner, uuid);
        FirebaseDatabase.getInstance().getReference().child("Orders").child(uuid).push().setValue(productModel);
        FirebaseDatabase.getInstance().getReference().child("Products").child(key).removeValue();
    }

    private void setDatainUI(String productOwner){
        DocumentReference docRef = db.collection("Users").document(productOwner);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        collegeName = document.getString("collegeName");
                        studentName = document.getString("username");

                        college.setText(collegeName);
                        student.setText(studentName);
                    } else {
                        Log.d("doc" , "document doesnt exists");
                    }
                } else {

                }
            }
        });
    }





}