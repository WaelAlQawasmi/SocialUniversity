package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.example.socialuniversityapp.R;

public class VerificationActivity extends AppCompatActivity {

    private static final String TAG = VerificationActivity.class.getSimpleName();
    private EditText mVerificationCode;
    private Button mVerifyButton;
    private TextView mTextError;
    private String email;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        mVerificationCode = findViewById(R.id.verification_code);
        mVerifyButton = findViewById(R.id.verify_button);
        mTextError = findViewById(R.id.verification_error_text);

        Intent intent = getIntent();
        email = intent.getStringExtra(SignUpActivity.EMAIL);

        mVerifyButton.setOnClickListener(mVerifyButtonClick);
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
                    error -> {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextError.setText("The Verification Code Isn't Correct");
                            }
                        });
                    }
            );
        }
    };
}