package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.example.socialuniversityapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static final String TAG = SplashScreenActivity.class.getSimpleName();
    ImageView mImageTop, mImageCenter, mImageBeat, mImageBottom;
    TextView mTextView;
    CharSequence charSequence;
    int index;
    long delay = 200;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        //inflate
        mImageTop = findViewById(R.id.iv_top);
//        mImageCenter = findViewById(R.id.iv_heart);
        mImageBeat = findViewById(R.id.iv_beat);
        mImageBottom = findViewById(R.id.iv_bottom);
        mTextView = findViewById(R.id.text_view);

        loginToMainActivityIf();


        //set full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //initialize top animation
        Animation animation1 = AnimationUtils.loadAnimation(this,R.anim.top_wave);
        //start top animation
        mImageTop.setAnimation(animation1);

        //initialize object animator
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(mImageCenter,
                PropertyValuesHolder.ofFloat("scaleX",1.2f),
                PropertyValuesHolder.ofFloat("scaleY",1.2f));
        //set duration
        objectAnimator.setDuration(500);
        //set repeat count
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        //set repeat mode
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        //Start animator
        objectAnimator.start();

        animationText("WELCOME !!!");

        Animation animation2 = AnimationUtils.loadAnimation(this,R.anim.bottom_wave);
        SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
        String Checkbox_true_or_false=preferences.getString("remember","false");
        mImageBottom.setAnimation(animation2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Log.e(TAG,Checkbox_true_or_false);
                if(Checkbox_true_or_false.equals("false")) {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                    finish();
                }


            }
        },4000);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            mTextView.setText(charSequence.subSequence(0,index++));

            if(index <= charSequence.length()){
                //When index is equal to text length
                //Run handler
                handler.postDelayed(runnable,delay);
            }
        }
    };

    public void animationText(CharSequence cs){
        charSequence = cs;
        index = 0;

        mTextView.setText("");

        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable,delay);
    }

public void loginToMainActivityIf(){
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
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    finish();
                },
                error -> {
                    Log.e("Error", error.toString());

                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                    Bundle bundle = new Bundle();
                    bundle.putString("Sign in",error.toString());

                    Message message = new Message();
                    message.setData(bundle);

                    handler.sendMessage(message);
                }
        );
    }
}
}