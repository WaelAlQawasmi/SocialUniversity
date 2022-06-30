package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.example.socialuniversityapp.R;

import java.util.List;

public class users_profile extends AppCompatActivity {
    private static final String TAG = users_profile.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profile);

        TextView name = findViewById(R.id.userName_profile);
        TextView major = findViewById(R.id.major_prof);

        Intent getuserId=getIntent();
        String username=getuserId.getStringExtra("username");
        RestOptions options = RestOptions.builder()
                .addPath("/User")
                .build();


        Amplify.API.query( ModelQuery.list(User.class,User.NAME.contains(username)),
                users -> {
                        for(User user:users.getData()){

                            name.setText(user.getName());
                            major.setText(user.getMajor());
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