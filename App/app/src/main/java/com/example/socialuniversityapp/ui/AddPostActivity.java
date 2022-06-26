package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.socialuniversityapp.R;


public class AddPostActivity extends AppCompatActivity {

    ImageView mImageIcon, mImageContent;
    EditText mPostContent;
    Button mAddPostButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        //Inflate

        mImageContent = findViewById(R.id.add_post_image_content);
        mImageIcon = findViewById(R.id.add_post_image_icon);
        mPostContent = findViewById(R.id.add_post_body);
        mAddPostButton = findViewById(R.id.add_post_button);





    }
}