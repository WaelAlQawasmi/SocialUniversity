package com.example.socialuniversityapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.amplifyframework.core.Amplify;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.databinding.ActivityNavagationBinding;
import com.google.android.material.navigation.NavigationView;


public class JobActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG =" out" ;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityNavagationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_job);




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




    private void logout() {
        Amplify.Auth.signOut(
                () -> {
                    Log.i(TAG, "Signed out successfully");
                    startActivity(new Intent(this, LoginActivity.class));
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

    private void navigateToJob() {


        Intent settingsIntent = new Intent(this, JobActivity.class);
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