package com.example.socialuniversityapp.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;

import com.amplifyframework.core.Amplify;
import com.example.socialuniversityapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {
    private static final String TAG = "Profile";
    public static final int REQUEST_CODE = 123;
    Handler handler;
    File file;
    String imageKey = null;
    String downImage ;
    CircleImageView newImage ;
    private final MediaPlayer mp = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView name = findViewById(R.id.userName_profile);
        TextView email = findViewById(R.id.email_profile);
        TextView major = findViewById(R.id.major_prof);
        TextView uniID = findViewById(R.id.uniId);
//        newImage = findViewById(R.id.imageView_profile);

        Amplify.Auth.fetchUserAttributes(
                attributes ->{
                    Bundle bundle =new Bundle();
                    bundle.putString("newUserName",attributes.get(3).getValue());
                    bundle.putString("newEmail",attributes.get(4).getValue());
                    bundle.putString("newMajor",attributes.get(5).getValue());
                    bundle.putString("UID",attributes.get(2).getValue());
                    Message message = new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);
                },
                error -> Log.e(TAG, "can't find username",error)
        );
        handler= new Handler(Looper.getMainLooper(), msg ->{
            String newUser= msg.getData().getString("newUserName");
            String newEmail= msg.getData().getString("newEmail");
            String newMajor= msg.getData().getString("newMajor");
            String uniId= msg.getData().getString("UID");
            name.setText(newUser);
            email.setText(newEmail);
            major.setText(newMajor);
            uniID.setText("ID : " +uniId);
            return true;
        });

        FloatingActionButton iamgeBtn = findViewById(R.id.imgBtn);
        iamgeBtn.setOnClickListener(view2 -> imageUpload());

        TextView logout = findViewById(R.id.logout_profile);
        logout.setOnClickListener(view -> {
            logout();
        });
    }

    public void imageUpload(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            // Handle error
            Log.e(TAG, "onActivityResult: Error getting image from device");
            return;
        }

        switch(requestCode) {
            case REQUEST_CODE:
                // Get photo picker response for single select.
                Uri currentUri = data.getData();

                String imgName= findViewById(R.id.imageView_profile).toString();
                // Do stuff with the photo/video URI.
                Log.i(TAG, "the uri is => " + currentUri);

                try {
                    Bitmap bitmap = getBitmapFromUri(currentUri);

                    file = new File(getApplicationContext().getFilesDir(), "profImg.jpg");
                    System.out.println(file.toString());
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.close();

                    // upload to s3
                    // uploads the file
                    Amplify.Storage.uploadFile(
                            "profImg.jpg",
                            file,
                            result -> {
                                Log.i(TAG, "Successfully uploaded: " + result.getKey()) ;
                                imageKey = result.getKey();
                                downloadImg(imageKey);
                            },
                            storageFailure -> Log.e(TAG, "Upload failed", storageFailure)
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
        }

    }
    //    /*
//       https://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically
//        */
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
    }

    private void downloadImg(String imageKey) {
        Amplify.Storage.downloadFile(
                imageKey,
                new File( getApplicationContext().getFilesDir() + "/" + imageKey),
                response -> {

                    Log.i(TAG, "Successfully downloaded: " + response.getFile().getName());
                    newImage = findViewById(R.id.imageView_profile);
                    Bitmap bitmap = BitmapFactory.decodeFile(getApplicationContext().getFilesDir()+"/"+ response.getFile().getName());
                    newImage.setImageBitmap(bitmap);

                },
                error -> Log.e(TAG,  "Download Failure", error)
        );
    }

    private void playAudio(InputStream audioData) {
        File mp3File = new File(getCacheDir(), "audio.mp3");

        try (OutputStream out = new FileOutputStream(mp3File)) {
            byte[] buffer = new byte[8 * 1_024];
            int bytesRead;
            while ((bytesRead = audioData.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            mp.reset();
            mp.setOnPreparedListener(MediaPlayer::start);
            mp.setDataSource(new FileInputStream(mp3File).getFD());
            mp.prepareAsync();
        } catch (IOException error) {
            Log.e("MyAmplifyApp", "Error writing audio file", error);
        }
    }

    private void logout() {
        Amplify.Auth.signOut(
                () -> {
                    Log.i(TAG, "Signed out successfully");
                    startActivity(new Intent(Profile.this, LoginActivity.class));
                    getAuthSession("logout");
                    finish();
                },
                error -> Log.e(TAG, error.toString())
        );
    }

    private void getAuthSession(String method) {
        Amplify.Auth.fetchAuthSession(
                result -> Log.i(TAG, "Auth Session => " + method + result.toString()),
                error -> Log.e(TAG, error.toString())
        );
    }
}