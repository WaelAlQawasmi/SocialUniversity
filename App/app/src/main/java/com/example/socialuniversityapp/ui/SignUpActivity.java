package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.socialuniversityapp.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText mFullName, mUniversityId, mMajor, mPassword, mEmail;
    private TextView mLoginLink;
    private Button mSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Inflate
        mFullName = findViewById(R.id.sign_up_fullName_text);
        mUniversityId =findViewById(R.id.sign_up_idNumber_text);
        mMajor = findViewById(R.id.sign_up_major_text);
        mPassword = findViewById(R.id.sign_up_password_text);
        mEmail = findViewById(R.id.sign_up_email_text);
        mLoginLink = findViewById(R.id.sign_up_toLogin);
        mSignUpButton = findViewById(R.id.sign_up_button);

        // Go To Login Activity
        mLoginLink.setOnClickListener(mLoginLinkClick);

        // Sign Up Button
        mSignUpButton.setOnClickListener(mSignUpButtonClick);

    }

    private final View.OnClickListener mSignUpButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private final View.OnClickListener mLoginLinkClick = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        }
    };
}