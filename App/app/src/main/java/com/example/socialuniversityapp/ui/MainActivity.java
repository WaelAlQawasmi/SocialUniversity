package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.socialuniversityapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navgationView);
        navigationView.setItemIconTintList(null);
        findViewById(R.id.toolbar).setOnClickListener(view -> {
            drawer.openDrawer(GravityCompat.START);
        });


        findViewById(R.id.fab).setOnClickListener(view -> {
            Intent messagesActivity=new Intent(this,messages.class);

            startActivity(messagesActivity);

        });




        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navagation);
        NavigationUI.setupWithNavController(navigationView, navController);


    }
}












