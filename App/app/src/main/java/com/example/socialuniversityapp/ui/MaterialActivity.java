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

public class MaterialActivity extends Fragment {
    private String username, imageKey;


    String[] states = {"new", "assigned", "in progress", "complete"};
    String[] teamsName = {"team1", "team2", "team3"};
    HashMap<String, String> teams = new HashMap<String, String>();
    public static final int REQUEST_CODE = 123;
    public static String IMGurl = "fileName";


    View root;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.activity_material, container, false);
        int myTasks = R.id.uplod_btn;
        Button upload = (Button) root.findViewById(R.id.uplod_btn);
        upload.setOnClickListener(view -> {

            imageUpload();

        });


        return root;

    }





    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }





    public void Upload() {

        EditText fileName = root.findViewById(R.id.fileName);
        String titleName = fileName.getText().toString();

        EditText fileDisc = root.findViewById(R.id.fileDisc);
        String bodyName = fileDisc.getText().toString();

        Material item = Material.builder()
                .fileName(titleName)
                .fileDis(bodyName)
                .fileUrl(IMGurl)
                .fileMajor("Engineering")
                .build();
        // Data store save

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
                    File file = new File(getActivity().getFilesDir(), "image.jpg");
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
                                IMGurl = result.getKey();
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
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*|application/pdf|audio/*");

        startActivityForResult(intent, REQUEST_CODE);
        Upload();
    }

    public void changUploadBotonColor() {
        Button upload = root.findViewById(R.id.uplod_btn);
        upload.setText("uploded!");
        upload.setBackgroundColor(this.getResources().getColor(R.color.error_color));
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
    }

}