package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.socialuniversityapp.R;

public class JobDescriptionActivity extends AppCompatActivity {

    private TextView mTitle, mPhone, mAddress, mDescription;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_description);

        mTitle = findViewById(R.id.job_desc_title);
        mPhone = findViewById(R.id.job_desc_phone);
        mAddress = findViewById(R.id.job_desc_address);
        mDescription = findViewById(R.id.job_desc_body);
        mImageView = findViewById(R.id.job_desc_image);

        getValueFromIntent();

    }

    public void getValueFromIntent() {
        Intent intent = getIntent();

        mTitle.setText(intent.getStringExtra("title"));
        mPhone.setText(intent.getStringExtra("phone"));
        mAddress.setText(intent.getStringExtra("address"));
        mDescription.setText(intent.getStringExtra("body"));

        String jobTitle = intent.getStringExtra("title");
        char firstChar = jobTitle.charAt(0);
        switch (firstChar) {
            case 'M':
                mImageView.setImageResource(R.drawable.marketing_image);
                break;
            case 'S':
                mImageView.setImageResource(R.drawable.science_image);
                break;
            case 'L':
                mImageView.setImageResource(R.drawable.law_image);
                break;
            case 'B':
                mImageView.setImageResource(R.drawable.business_image);
                break;
            case 'H':
                mImageView.setImageResource(R.drawable.health_image);
                break;
            case 'I':
                mImageView.setImageResource(R.drawable.it_image);
                break;
        }
    }
}