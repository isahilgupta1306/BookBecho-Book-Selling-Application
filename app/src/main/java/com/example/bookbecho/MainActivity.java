package com.example.bookbecho;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.bookbecho.fragments.ChatFragment;
import com.example.bookbecho.fragments.FavoriteProd;
import com.example.bookbecho.fragments.addProductForm;
import com.example.bookbecho.fragments.cart;
import com.example.bookbecho.fragments.home_fragment;
import com.example.bookbecho.fragments.myOrders;
import com.example.bookbecho.fragments.myProducts;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private  String baseUrl = "https://emailauthentication-72f49-default-rtdb.firebaseio.com/" ;
    FirebaseAuth auth;
    Toolbar myToolbar;
    NavigationView navview;
    Menu menu;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    FirebaseFirestore db;
    AppCompatTextView userName;
    public String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        logOut = findViewById(R.id.logout);

        auth = FirebaseAuth.getInstance();
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        userName = (AppCompatTextView)findViewById(R.id.drawerName);
        navview =(NavigationView)findViewById(R.id.nav_menu);
//        navview.getMenu();
        View headview = navview.getHeaderView(0);
        userName = headview.findViewById(R.id.drawerName);

        setSupportActionBar(myToolbar);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance(baseUrl);
        DatabaseReference myRef = database.getReference("message");

        //Adding Data To App Drawer
        db = FirebaseFirestore.getInstance();
        setData();
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();



        drawerToggle = new ActionBarDrawerToggle(this , drawerLayout , myToolbar , R.string.open , R.string.close );
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        //Default Frag
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , new home_fragment()).commit();
        navview.setCheckedItem(R.id.home);

        navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment tempFrag ;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home :
                        tempFrag = new home_fragment();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , tempFrag).commit();
                        break;
                    case R.id.productHistory:
                         tempFrag = new myOrders();    //for adding new fragPiece
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , tempFrag).commit();
                        break;
                    case R.id.chatHistory:
                        tempFrag = new ChatFragment();    //for adding new fragPiece
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , tempFrag).commit();
                        break;
                    case R.id.myProducts:
                        tempFrag = new myProducts();    //for adding new fragPiece
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , tempFrag).commit();
                        break;
                    case R.id.favorites:
                        tempFrag = new FavoriteProd();    //for adding new fragPiece
                        drawerLayout.closeDrawer(GravityCompat.START);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , tempFrag).commit();
                        break;
                    case R.id.logout :
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), login.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    public void onClickAddProduct(MenuItem menuItem) {
        Fragment tempFrag;
        tempFrag = new addProductForm();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , tempFrag).commit();
    }
    public  void onClickAddToCart(MenuItem menuItem){
        Fragment tempFrag;
        tempFrag = new cart();
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , tempFrag).commit();
    }
    private void setData(){
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if(!currentuser.isEmpty()){
            DocumentReference docRef = db.collection("Users").document(currentuser);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            studentName = document.getString("username");
                            Log.d("methodCheck" , "text +" + studentName);
                            userName.setText(studentName);
                        } else {
                            Log.d("doc" , "document doesnt exists");
                        }
                    } else {
                        Log.d("doc" , "task unsuccesful");
                    }
                }
            });
        }
    }


}
