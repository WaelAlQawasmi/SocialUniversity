package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.amplifyframework.core.Amplify;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.databinding.ActivityNavagationBinding;
import com.example.socialuniversityapp.recycler_view.JobRecyclerView;
import com.example.socialuniversityapp.recycler_view.MajorPostActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG =" out" ;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavagationBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityNavagationBinding.inflate(getLayoutInflater());
 setContentView(binding.getRoot());


        setSupportActionBar(binding.appBarNavagation.toolbar);



        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navagation);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, findViewById(R.id.activity_main_drawer), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

    }







    ///////////////////////MENU//////////////////////


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case R.id.nav_profile:

                navigateToProfile();
                return true;

            case R.id.nav_wether:
                navigateTomaterial();

                return true;

            case R.id.nav_jobs:

                navigateToJob();
                return true;

            case R.id.nav_logout:

                logout();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void logout() {
        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("remember","false");
        editor.apply();
        Amplify.Auth.signOut(
                () -> {
                    Log.i(TAG, "Signed out successfully");
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    authSession("logout");
                    finish();
                },
                error -> Log.e(TAG, error.toString())
        );

    }

    private void navigateToProfile() {


        Intent settingsIntent = new Intent(this, MainActivity.class);
        startActivity(settingsIntent);
    }

    private void navigateToWeher() {


        Intent settingsIntent = new Intent(this, MainActivity.class);
        startActivity(settingsIntent);
    }

    private void navigateTomaterial() {


        Intent settingsIntent = new Intent(this, materialAndPostsActivity.class);
        startActivity(settingsIntent);
    }

    private void navigateToJob() {


        Intent settingsIntent = new Intent(this, JobRecyclerView.class);
        startActivity(settingsIntent);
    }
    private void navigateToMajorPostActivity() {



        Intent settingsIntent = new Intent(this, MaterialActivity.class);
        startActivity(settingsIntent);
    }

    ///////////////////////MENU   END//////////////////////

    private void authSession(String method) {
        Amplify.Auth.fetchAuthSession(
                result -> Log.i(TAG, "Auth Session => " + method + result.toString()),
                error -> Log.e(TAG, error.toString())
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navagation);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:

                navigateToProfile();
                return true;

            case R.id.nav_wether:

                navigateToMajorPostActivity();
                return true;

            case R.id.nav_jobs:

                navigateToJob();
                return true;

            case R.id.nav_logout:

                logout();
                return true;}
        return false;
    }
}












