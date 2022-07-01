package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.UniPost;
import com.amplifyframework.datastore.generated.model.User;
import com.example.socialuniversityapp.R;

import java.util.ArrayList;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    public static final String EMAIL = "email";
    private EditText mFullName, mUniversityId, mPassword, mEmail;
    private AutoCompleteTextView mAutoCompleteTextView;
    private TextView mLoginLink;
    private Button mSignUpButton;
    private ProgressBar mLoadingProgressBar;
    private AlertDialog.Builder builder;

    private String majorName;
    String[] items = {"Computer Science", "Software Eng", "AI", "Networking Sec", "Data Analysis", "Math", "Physics", "History"};
    ArrayAdapter<String> adapterItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Inflate
        mFullName = findViewById(R.id.sign_up_fullName_text);
        mUniversityId =findViewById(R.id.sign_up_idNumber_text);
        mAutoCompleteTextView = findViewById(R.id.sign_up_major_text);
        mPassword = findViewById(R.id.sign_up_password_text);
        mEmail = findViewById(R.id.sign_up_email_text);
        mLoginLink = findViewById(R.id.sign_up_toLogin);
        mSignUpButton = findViewById(R.id.sign_up_button);
        mLoadingProgressBar = findViewById(R.id.loading);

        builder = new AlertDialog.Builder(this);

        adapterItem = new ArrayAdapter<>(this, R.layout.list_item, items);
        mAutoCompleteTextView.setAdapter(adapterItem);

        // auto Complete Click
        mAutoCompleteTextView.setOnItemClickListener(autoCompleteTextViewClick);

        // Go To Login Activity
        mLoginLink.setOnClickListener(mLoginLinkClick);

        // Sign Up Button
        mSignUpButton.setOnClickListener(mSignUpButtonClick);


    }

    // auto Complete Click
    private final AdapterView.OnItemClickListener autoCompleteTextViewClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            char currentValue = adapterView.getItemAtPosition(position).toString().charAt(0);
            switch (currentValue){
                case 'C':
                case 'S':
                case 'A':
                case 'N':
                case 'D':
                case 'P':
                case 'M':
                    majorName = adapterView.getItemAtPosition(position).toString();
                    break;

            }

        }
    };

    private final View.OnClickListener mSignUpButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mLoadingProgressBar.setVisibility(View.VISIBLE);
            int count = 0;
            if (mEmail.getText().toString().contains("@") == false){
                mLoadingProgressBar.setVisibility(View.INVISIBLE);
                mEmail.setError("Enter a correct Your Email");
                count++;
            } if (mPassword.getText().length() < 8){
                mLoadingProgressBar.setVisibility(View.INVISIBLE);
                mPassword.setError("Your password must be grater than 8 char or ");
                count++;
            }
            if (mFullName.getText().toString().equals("")){
                mLoadingProgressBar.setVisibility(View.INVISIBLE);
                mFullName.setError("Please enter your FullName");
                count++;
            }
            if (mUniversityId.getText().toString().length() < 5){
                mLoadingProgressBar.setVisibility(View.INVISIBLE);
                mUniversityId.setError("University Id must be grater than 5 numbers");
                count++;
            }

            if (count == 0) {
                signUp(mEmail.getText().toString(),
                        mPassword.getText().toString(),
                        mFullName.getText().toString(),
                        majorName,
                        mUniversityId.getText().toString());
            }
        }
    };

    private final View.OnClickListener mLoginLinkClick = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        }
    };

    private void signUp(String email, String password, String user_name, String major, String uniId) {

        mLoadingProgressBar = findViewById(R.id.loading);

        // create a list of attributes
        List<AuthUserAttribute> attributes=new ArrayList<>();
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.email(), email));
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.nickname(),user_name));
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.custom("custom:universityId"), uniId));
        attributes.add(new AuthUserAttribute(AuthUserAttributeKey.custom("custom:majoreName"), major));
        User newUser = User
                .builder()
                .cognitoId(uniId)
                .name(user_name)
                .major(major)
                .build();

        Amplify.API.mutate(ModelMutation.create(newUser),
                success -> {
                  Log.i(TAG,"saved New user in database");
                },
                error ->{
                    Log.e(TAG, "sign up error ", error);
                });


        Amplify.Auth.signUp(email, password,AuthSignUpOptions.builder().userAttributes(attributes).build(),


                result -> {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLoadingProgressBar.setVisibility(View.INVISIBLE);
                        }
                                      });
                    Intent intent = new Intent(SignUpActivity.this, VerificationActivity.class);
                    intent.putExtra(EMAIL, email);
                    startActivity(intent);

                    finish();
                },
                error -> {
                    Log.e(TAG, "Sign up failed", error);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLoadingProgressBar.setVisibility(View.INVISIBLE);
                            mEmail.setError("Email is Already Exists");
                        }
                    });
                }
        );

    }
}
