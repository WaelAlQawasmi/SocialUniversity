package com.example.socialuniversityapp.ui;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.MajorPost;
import com.amplifyframework.datastore.generated.model.UniPost;
import com.example.socialuniversityapp.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;


public class AddPostActivity extends AppCompatActivity {

    private static final String TAG = AddPostActivity.class.getSimpleName();
    public static final int REQUEST_CODE = 123;

    private ImageView mImageIcon, mImageContent;
    private EditText mPostContent;
    private Button mAddPostButton;
    private String username, imageKey, majorName, context;

    Handler handler;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        //Inflate
        mImageContent = findViewById(R.id.add_post_image_content);
        mImageIcon = findViewById(R.id.add_post_image_icon);
        mPostContent = findViewById(R.id.add_post_body);
        mAddPostButton = findViewById(R.id.add_post_button);



        // To Know From Any Activity This Intent Came
        Intent intent = getIntent();

        if (intent.getStringExtra("context").equals("University"))
            context = intent.getStringExtra("context");
        else if (intent.getStringExtra("context").equals("Major"))
            context = intent.getStringExtra("context");

        // Fetch Authentication User From Cloud

        Amplify.Auth.fetchUserAttributes(
                attributes -> {
                    Log.i("AuthDemo", "User attributes = " + attributes.toString());
                    attributes.forEach(authUserAttribute -> {

                        if (authUserAttribute.getKey().getKeyString().equals("nickname"))
                            username=authUserAttribute.getValue();


                        if (authUserAttribute.getKey().getKeyString().equals("custom:majoreName"))
                            majorName = authUserAttribute.getValue();
                    });
                },
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );

        // Add Post Button
        mAddPostButton.setOnClickListener(addPostButtonClick);

        // Add Image Icon
        mImageIcon.setOnClickListener(addImageIconClick);

        // Load Image


    }

    // Add Post Button Click
    private final View.OnClickListener addPostButtonClick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            UniPost uniPost = UniPost
                    .builder()
                    .userName(username)
                    .body(mPostContent.getText().toString())
                    .image(imageKey)
                    .build();

            MajorPost majorPost = MajorPost
                    .builder()
                    .userName(username)
                    .major(majorName)
                    .body(mPostContent.getText().toString())
                    .image(imageKey)
                    .build();

            if (context.equals("University")){
                Amplify.API.mutate(ModelMutation.create(uniPost),
                        success -> {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mPostContent.setText("");
                                    mImageContent.setVisibility(View.GONE);
                                }
                            });
                        },
                        filed ->{});

            } else if (context.equals("Major")){
                Amplify.API.mutate(ModelMutation.create(majorPost),
                        success -> {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mPostContent.setText("");
                                    mImageContent.setVisibility(View.GONE);
                                }
                            });
                        },
                        filed ->{});
            }
        }
    };

    private final View.OnClickListener addImageIconClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            imageUpload();
            imageContentLoad();
        }
    };

    public void imageUpload() {
        // Launches photo picker in single-select mode.
        // This means that the user can select one photo or video.
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            // Handler error
            Log.e(TAG, "onActivityResult: Error getting image from device");
        }
        switch (requestCode) {
            case REQUEST_CODE:
                // Get photo picker response for single select.
                Uri currentUri = data.getData();
                // Do stuff with the photo/video URI
                Log.i(TAG, "onActivityResult: the uri is => " + currentUri);

                try {
                    Bitmap bitmapImage = getBitmapFromUri(currentUri);

                    // Convert Bitmap to File
                    File file = new File(getApplicationContext().getFilesDir(), "image.jpg");
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                    bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.close();

                    // upload file to s3
                    imageKey = UUID.randomUUID().toString();

                    Amplify.Storage.uploadFile(
                            imageKey + ".jpg",
                            file,
                            result -> {
                                Log.i(TAG, "Successfully uploaded: " + result.getKey());


                            },
                            storageFailure -> Log.e(TAG, "Failed Upload", storageFailure)

                    );


                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
        }

    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
    }

    private void imageContentLoad(){

        Amplify.Storage.getUrl(
                imageKey+".jpg",
                success ->{
                    Bundle bundle = new Bundle();
                    bundle.putString("url", success.getUrl().toString());

                    Message message = new Message();
                    message.setData(bundle);

                    handler.sendMessage(message);

                    Log.i(TAG, "image " + success.getUrl());
                },
                error -> {
                    Log.i(TAG, "image Error : " + error);
                });

        // Handler
        handler = new Handler(Looper.getMainLooper(), msg -> {
            String imageUrl = msg.getData().getString("url");
            mImageContent.setVisibility(View.VISIBLE);
            Picasso.get().load(imageUrl).into(mImageContent);
            Toast.makeText(this, "Post Successful", Toast.LENGTH_SHORT).show();
            return true;
        });
    }
}