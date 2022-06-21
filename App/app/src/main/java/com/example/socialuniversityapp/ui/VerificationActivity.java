package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.example.socialuniversityapp.R;

public class VerificationActivity extends AppCompatActivity {

    private static final String TAG = VerificationActivity.class.getSimpleName();
    private EditText mVerificationCode;
    private Button verifyButton;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        mVerificationCode = findViewById(R.id.verification_code);
        verifyButton = findViewById(R.id.verify_button);

        Intent intent = getIntent();
        email = intent.getStringExtra(SignUpActivity.EMAIL);

        verifyButton.setOnClickListener(mVerifyButtonClick);
    }

    private final View.OnClickListener mVerifyButtonClick= new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Amplify.Auth.confirmSignUp(
                    email,
                    mVerificationCode.getText().toString(),
                    result -> {
                        Log.i(TAG, result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete");

                        startActivity(new Intent(VerificationActivity.this, LoginActivity.class));
                        finish();
                    },
                    error -> Log.e(TAG, error.toString())
            );
        }
    };
}