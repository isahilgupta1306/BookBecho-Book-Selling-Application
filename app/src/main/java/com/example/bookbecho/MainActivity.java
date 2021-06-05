package com.example.bookbecho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
//    Button logOut;
    FirebaseAuth auth;
    Toolbar myToolbar;
    NavigationView navview;
    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;

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
        navview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home :
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.profile :
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.logout :
                        drawerLayout.closeDrawer(GravityCompat.START);
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getApplicationContext(), login.class));
                        finish();
                        break;
                }
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




}
