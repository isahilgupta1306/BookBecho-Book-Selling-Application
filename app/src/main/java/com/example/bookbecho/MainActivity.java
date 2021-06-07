package com.example.bookbecho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bookbecho.fragments.addProductForm;
import com.example.bookbecho.fragments.cart;
import com.example.bookbecho.fragments.home_fragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
//    Button logOut;
    FirebaseAuth auth;
    Toolbar myToolbar;
    NavigationView navview;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    MenuItem menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        logOut = findViewById(R.id.logout);

        auth = FirebaseAuth.getInstance();
        navview =(NavigationView)findViewById(R.id.nav_menu);
        myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(myToolbar);


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
                        break;
                    case R.id.profile :
                         //tempFrag = new home_fragment();    //for adding new fragPiece
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.logout :
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), login.class));
                        finish();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , tempFrag).commit();
                return true;
            }
        });

        //        logOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(getApplicationContext(), login.class));
//                finish();
//            }
//        });
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


}
