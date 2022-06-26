package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.amplifyframework.core.Amplify;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.databinding.ActivityNavagationBinding;
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

public class NavagationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG =" out" ;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavagationBinding binding;
    private Object NavagationActivity;
    Activity ui= (Activity) NavagationActivity;
public  void integration(Activity ui){
    this.ui=ui;

}
    @Override
    protected void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        binding = ActivityNavagationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarNavagation.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(     ui, R.id.nav_host_fragment_content_navagation);
        NavigationUI.setupActionBarWithNavController((AppCompatActivity) ui, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                ui, drawer, findViewById(R.id.activity_main_drawer), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
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




    protected void logout() {
        Amplify.Auth.signOut(
                () -> {
                    Log.i(TAG, "Signed out successfully");
                    startActivity(new Intent(ui, LoginActivity.class));
                    authSession("logout");
                    finish();
                },
                error -> Log.e(TAG, error.toString())
        );

    }



    protected void navigateToProfile() {


        Intent settingsIntent = new Intent(ui, MainActivity.class);
        startActivity(settingsIntent);
    }

    protected void navigateToWeher() {


        Intent settingsIntent = new Intent(ui, MainActivity.class);
        startActivity(settingsIntent);
    }

    private void navigateToJob() {


        Intent settingsIntent = new Intent(ui, JobActivity.class);
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












