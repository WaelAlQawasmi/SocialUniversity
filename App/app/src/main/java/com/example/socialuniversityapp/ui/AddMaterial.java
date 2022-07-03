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
import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;

import com.amplifyframework.datastore.generated.model.Material;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;


import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.amplifyframework.storage.options.StorageUploadFileOptions;
import com.example.socialuniversityapp.R;

public class AddMaterial extends AppCompatActivity{
    private String username, imageKey;
    String titleName;

    HashMap<String, String> teams = new HashMap<String, String>();
    public static final int REQUEST_CODE = 123;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);

        Button upload = findViewById(R.id.uplod_btn);
        upload.setOnClickListener(view -> {

            imageUpload();

        });
    }







    public void Upload() {

        EditText fileName = findViewById(R.id.fileName);
         titleName = fileName.getText().toString();

        EditText fileDisc = findViewById(R.id.fileDisc);
        String bodyName = fileDisc.getText().toString();

        Material item = Material.builder()
                .fileName(titleName)
                .fileDis(titleName)
                .fileUrl(imageKey)
                .fileMajor("Engineering")
                .build();


        Amplify.API.mutate(ModelMutation.create(item),  success -> Log.i(TAG, "Saved item: " ),
                error -> Log.e(TAG, "Could not save item to DataStore", error));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
                    File file = new File(getFilesDir(), titleName+".jpg");
                    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
                    bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, os);
                    os.close();

                    // upload file to s3
                    imageKey = UUID.randomUUID().toString();
                    Amplify.Storage.uploadFile(
                            imageKey + data.getType(),
                            file,
                            result ->  {
                                Log.i(TAG, "Successfully uploaded: " + result.getKey());
                                changUploadBotonColor();

                            },
                            storageFailure -> Log.e(TAG, "Failed Upload", storageFailure)

                    );


                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
        }

    }


    public void imageUpload() {
        // Launches photo picker in single-select mode.
        // This means that the user can select one photo or video.
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*|application/pdf|audio/*");

        startActivityForResult(intent, REQUEST_CODE);
        Upload();
    }

    public void changUploadBotonColor() {
        Button upload =findViewById(R.id.uplod_btn);
        upload.setText("uploded!");
        upload.setBackgroundColor(this.getResources().getColor(R.color.error_color));
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
    }

}