package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.api.rest.RestOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.UniPost;
import com.amplifyframework.datastore.generated.model.User;
import com.amplifyframework.storage.StorageItem;
import com.example.socialuniversityapp.R;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class users_profile extends AppCompatActivity {
    private static final String TAG = users_profile.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profile);

        TextView name = findViewById(R.id.userName_profile);
        TextView major = findViewById(R.id.major_prof);
        CircleImageView image=findViewById(R.id.imageView_User_profile);

        Intent getuserId=getIntent();

        String username=getuserId.getStringExtra("username");



        Amplify.API.query( ModelQuery.list(User.class,User.NAME.contains(username)),
                users -> {
                           User user=null;
                        for(User temp_user:users.getData()){

                            name.setText(temp_user.getName());
                            major.setText(temp_user.getMajor());
                            user=temp_user;
                            break;
                        }
                    if (user != null) {
                        User finalUser = user;


                    Amplify.Storage.downloadFile(
                            user.getCognitoId()+".jpg",
                            new File(this.getFilesDir() + "/" + user.getCognitoId()+".jpg"),
                            response -> {

                                Log.i(TAG, "Successfully downloaded: " + response.getFile().getName());
                                Bitmap bitmap = BitmapFactory.decodeFile(this.getApplicationContext().getFilesDir() + "/" + response.getFile().getName());
                                image.setImageBitmap(bitmap);

                            },
                            error -> Log.e(TAG, "Download Failure", error)
                    );
                }


                        Log.e(TAG, "out of loop");

                    }
                ,
                error -> {
                    name.setText("name");
                    major.setText("major");

                    Log.e(TAG, "Could not query Api", error);
                });



}}