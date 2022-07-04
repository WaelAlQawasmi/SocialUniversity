package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;
import com.example.socialuniversityapp.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import androidx.navigation.ui.NavigationUI;

import java.io.File;


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
        String email=Amplify.Auth.getCurrentUser().getUsername();

        Amplify.API.query(ModelQuery.list(User.class,User.EMAIL.contains(email)), users ->{
            User user=null;
            if(users.hasData()){
                for (User temp_user:users.getData()){
                    user=temp_user;
                }
                View headerView = navigationView.getHeaderView(0);

                TextView header_name = headerView.findViewById(R.id.header_name);
                TextView header_email = headerView.findViewById(R.id.header_email);
                User finalUser = user;
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        if (finalUser != null) {
                            header_name.setText(finalUser.getName());
                            header_email.setText(finalUser.getEmail());
                            downloadImg(finalUser.getCognitoId()+".jpg",headerView);
                        }
                    }
                });

                }


        },error ->{

        });






        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_navagation);
        NavigationUI.setupWithNavController(navigationView, navController);


    }
    private void downloadImg(String imageKey,View header) {
        Amplify.Storage.downloadFile(
                imageKey,
                new File(this.getFilesDir() + "/" + imageKey),
                response -> {

                    Log.i(TAG, "Successfully downloaded: " + response.getFile().getName());
                    ImageView newImage =header.findViewById(R.id.header_image);
                    Bitmap bitmap = BitmapFactory.decodeFile(this.getApplicationContext().getFilesDir() + "/" + response.getFile().getName());
                    newImage.setImageBitmap(bitmap);

                },
                error -> Log.e(TAG, "Download Failure", error)
        );
    }
}












