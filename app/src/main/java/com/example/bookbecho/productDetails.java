package com.example.bookbecho;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ImageView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.bookbecho.databinding.ActivityProductDetailsBinding;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class productDetails extends AppCompatActivity {

    DatabaseReference database;
    String productDescription , productTitle , productPrice , productImageUrl;
    MaterialTextView description , price , title ;
    MaterialButton addToCart , buyNow , chatWithSeller;
    ImageView productImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_product_details);
        database = FirebaseDatabase.getInstance().getReference().child("Products");
        //Hooks
        title = (MaterialTextView)findViewById(R.id.productTitle);
        price = (MaterialTextView)findViewById(R.id.productPrice);
        description = (MaterialTextView)findViewById(R.id.productDescription);

        addToCart = (MaterialButton)findViewById(R.id.addtoCart);
        buyNow = (MaterialButton)findViewById(R.id.buyButton);
        chatWithSeller = (MaterialButton)findViewById(R.id.chatwithuser);

        productImage = (ImageView)findViewById(R.id.productImage);

        //Our key
        String key = getIntent().getStringExtra("key");
        database.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    productDescription = snapshot.child("description").getValue().toString();
                    productImageUrl = snapshot.child("photo").getValue().toString();
                    productTitle = snapshot.child("title").getValue().toString();
                    productPrice = snapshot.child("price").getValue().toString();

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

        System.out.println(productDescription);




    }


}