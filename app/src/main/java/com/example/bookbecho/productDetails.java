package com.example.bookbecho;

import android.content.DialogInterface;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import org.jetbrains.annotations.NotNull;

public class productDetails extends AppCompatActivity {

    DatabaseReference database , profileInfo;
    String productDescription , productTitle , productPrice , productImageUrl , soldStatus;
    MaterialTextView description , price , title , college , student;
    MaterialButton addToCart , buyNow , chatWithSeller;
    ImageView productImage;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_product_details);
        database = FirebaseDatabase.getInstance().getReference().child("Products");
        profileInfo = FirebaseDatabase.getInstance().getReference().child("Users");
        //Hooks
        title = (MaterialTextView)findViewById(R.id.productTitle);
        price = (MaterialTextView)findViewById(R.id.productPrice);
        description = (MaterialTextView)findViewById(R.id.productDescription);
        college = (MaterialTextView)findViewById(R.id.collegeName);
        student = (MaterialTextView)findViewById(R.id.studentName);

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

        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog(key);
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

    private void buynow(String key){
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database.child(key).child("sold").setValue(currentuser);
    }



}