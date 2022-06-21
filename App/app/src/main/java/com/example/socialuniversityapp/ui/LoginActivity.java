package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.socialuniversityapp.R;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button mLoginButton;
    private TextView mSignUpLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inflate
        mEmail = findViewById(R.id.login_email_text);
        mPassword = findViewById(R.id.login_password_text);
        mLoginButton = findViewById(R.id.login_button);
        mSignUpLink = findViewById(R.id.login_toSign_up);

        // Go To SignUp Activity
        mSignUpLink.setOnClickListener(mSignUpLinkClick);

        // Login Button Click
        mLoginButton.setOnClickListener(mLoginButtonClick);
    }

    // Sign Up Link ClickListener
    private final View.OnClickListener mSignUpLinkClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        }
    };

    // Login Button Click
    private  final View.OnClickListener mLoginButtonClick = new View.OnClickListener(){
        @Override
        public void onClick(View view) {

        }
    };
}