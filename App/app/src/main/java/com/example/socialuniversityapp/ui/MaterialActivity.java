package com.example.socialuniversityapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import android.util.Log;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

import com.amplifyframework.storage.s3.AWSS3StoragePlugin;



import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import com.amplifyframework.storage.options.StorageUploadFileOptions;
import com.example.socialuniversityapp.R;

public class MaterialActivity extends Fragment {


    String[] states = {"new", "assigned", "in progress", "complete"};
    String[] teamsName = {"team1", "team2", "team3"};
    HashMap<String, String> teams = new HashMap<String, String>();
    public static final int REQUEST_CODE = 123;
    public static String IMGurl = null;


   
      
     
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        
       View root= inflater.inflate(R.layout.activity_material, container, false);
       int myTasks =root.R.id.uplod_btn;
  Button upload =(Button) findViewById(root.R.id.uplod_btn);
        upload.setOnClickListener(view -> {
            try {
                uploadFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
return root;

    }



    private void pictureUpload() {
        // Launches photo picker in single-select mode.
        // This means that the user can select one photo or video.

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }




    private void uploadFile() throws IOException {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select file to upload "), 8);
        Uri uri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);



        File exampleFile = BitMapHandler(uri, "hi.jpg");



        BufferedWriter writer = new BufferedWriter(new FileWriter(exampleFile));


        Log.e(TAG, "Upload failed " + "titleName");
        Amplify.Storage.uploadFile(
                "titleName" + ".jpg",
                exampleFile,
                result -> {
                    Log.i(TAG, "Successfully uploaded: " + result.getKey());
                    IMGurl = result.getKey();
                    changUploadBotonColor();
                },
                storageFailure -> Log.e(TAG, "Upload failed", storageFailure)

        );
    }





    private File BitMapHandler(Uri currentUri, String titleName) throws IOException {
        Bitmap bitmap = getBitmapFromUri(currentUri);

        File file = new File(getApplicationContext().getFilesDir(), titleName + ".jpg");
        OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        os.close();
        return file;
    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
    }
    public void changUploadBotonColor() {
        Button upload = findViewById(R.id.uplod_btn);
        upload.setText("uploded!");
        upload.setBackgroundColor(this.getResources().getColor(R.color.browser_actions_bg_grey));
    }





}