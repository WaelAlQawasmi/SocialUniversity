package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.recycler_view.JobRecyclerView;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private EditText mEmail, mPassword;
    private Button mLoginButton;
    private TextView mSignUpLink;
    private ProgressBar mLoadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Inflate
        mEmail = findViewById(R.id.login_email_text);
        mPassword = findViewById(R.id.login_password_text);
        mLoginButton = findViewById(R.id.login_button);
        mSignUpLink = findViewById(R.id.login_toSign_up);
        mLoadingProgressBar = findViewById(R.id.loading_login);

        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String Checkbox_true_or_false=preferences.getString("remember","");
         Log.e(TAG,Checkbox_true_or_false);
        if(Checkbox_true_or_false.equals("true")){
            String stored_email=preferences.getString("email","");
            String stored_password=preferences.getString("password","");

            Amplify.Auth.signIn(
                    stored_email,
                    stored_password,
                    result -> {
                        Log.i(TAG, result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");

                        runOnUiThread(new Runnable() {
                                          @Override
                                          public void run() {
                                              mLoadingProgressBar.setVisibility(View.INVISIBLE);
                                          }
                        });
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    },
                    error -> Log.e(TAG, error.toString())
            );
        }



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
            mLoadingProgressBar.setVisibility(View.VISIBLE);
            login(mEmail.getText().toString(), mPassword.getText().toString());
        }

    };


    private void login(String email, String password) {
        CheckBox rememberMeCheckBox=findViewById(R.id.remember_me);

        if(rememberMeCheckBox.isChecked()){
            SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("remember","true");
            editor.putString("email",email);
            editor.putString("password",password);
            editor.apply();
        }
        else{
            SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("remember","false");
            editor.apply();
        }
        Amplify.Auth.signIn(
                email,
                password,
                result -> {
                    Log.i(TAG, result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");

                    mLoadingProgressBar.setVisibility(View.INVISIBLE);

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    finish();
                },
                error -> Log.e(TAG, error.toString())
        );
    }
}