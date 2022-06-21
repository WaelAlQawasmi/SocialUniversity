package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.socialuniversityapp.R;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    private EditText mFullName, mUniversityId, mMajor, mPassword, mEmail;
    private TextView mLoginLink;
    private Button mSignUpButton;
    private ProgressBar mLoadingProgressBar;
    private AlertDialog.Builder builder;

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
        mLoadingProgressBar = findViewById(R.id.loading);

        builder = new AlertDialog.Builder(this);

        // Go To Login Activity
        mLoginLink.setOnClickListener(mLoginLinkClick);

        // Sign Up Button
        mSignUpButton.setOnClickListener(mSignUpButtonClick);

    }

    private final View.OnClickListener mSignUpButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mLoadingProgressBar.setVisibility(View.VISIBLE);
            signUp(mEmail.getText().toString(),
                    mPassword.getText().toString(),
                    mFullName.getText().toString(),
                    mMajor.getText().toString(),
                    mUniversityId.getText().toString());
        }
    };

    private final View.OnClickListener mLoginLinkClick = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        }
    };

    private void signUp(String email, String password, String user_name, String major, String uniId) {
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), email)
                .userAttribute(AuthUserAttributeKey.nickname(), user_name)
                .userAttribute(AuthUserAttributeKey.custom("major"), major)
                .userAttribute(AuthUserAttributeKey.custom("uniId"), uniId)
                .build();

        Amplify.Auth.signUp(email, password, options,
                result -> {
                    Log.i(TAG, "Result: " + result.toString());
                    mLoadingProgressBar.setVisibility(View.INVISIBLE);

                    Intent intent = new Intent(SignUpActivity.this, VerificationActivity.class);
                    startActivity(intent);

                    finish();
                },
                error -> {
                    Log.e(TAG, "Sign up failed", error);
                    mLoadingProgressBar.setVisibility(View.INVISIBLE);

                    builder.setMessage(error.getMessage()).setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog alert = builder.create();

                            alert.setTitle("Email Already Exists");
                            alert.show();
                        }
                    });

                }
        );

    }
}