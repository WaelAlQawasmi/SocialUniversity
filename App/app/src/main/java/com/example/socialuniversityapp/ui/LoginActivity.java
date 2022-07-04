package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
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
    private TextView mSignUpLink, mEmailError, mEmailAndPasswordError;
    private ProgressBar mLoadingProgressBar;

    private Handler handler;

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
        mEmailError = findViewById(R.id.email_error_text);
        mEmailAndPasswordError = findViewById(R.id.email_password_error_text);






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
            mEmailAndPasswordError.setText("");
            int count = 0;
            if (mEmail.getText().toString().contains("@") == false){
                mLoadingProgressBar.setVisibility(View.INVISIBLE);
                mEmail.setError("Please Enter correct Email");
                count++;
            }
            if (mPassword.getText().toString().equals("")){
                mLoadingProgressBar.setVisibility(View.INVISIBLE);
                mPassword.setError("Please Enter your password ");
                count++;
            }
            if (count ==0)
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
                    runOnUiThread(()->{
                        mLoadingProgressBar.setVisibility(View.INVISIBLE);

                    });

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                    finish();
                },
                error -> {
                    Log.e("Error", error.toString());

                    Bundle bundle = new Bundle();
                    bundle.putString("Sign in",error.toString());

                    Message message = new Message();
                    message.setData(bundle);

                    handler.sendMessage(message);
                }
        );
        // Handler
        handler = new Handler(Looper.getMainLooper(), msg ->{
            String msgHandler = msg.getData().getString("Sign in");

            if (msgHandler.contains("UserNotFoundException")) {
                mEmail.setError("Email isn't correct");
                mLoadingProgressBar.setVisibility(View.GONE);
            }
            else if (msgHandler.contains("NotAuthorizedException")){
                mPassword.setError("Please enter correct email and password");
                mLoadingProgressBar.setVisibility(View.GONE);
            }else  {
                mEmailAndPasswordError.setVisibility(View.VISIBLE);
                mLoadingProgressBar.setVisibility(View.GONE);
            }
            return true;
        });
    }


}