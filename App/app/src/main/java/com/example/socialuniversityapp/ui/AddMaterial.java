package com.example.socialuniversityapp.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.widget.Button;
import android.widget.EditText;

import android.util.Log;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;

import com.amplifyframework.datastore.generated.model.Material;


import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.UUID;

import com.amplifyframework.storage.options.StorageUploadFileOptions;
import com.example.socialuniversityapp.R;

import org.chromium.base.FileUtils;

public class AddMaterial extends AppCompatActivity {

    EditText filename;
    EditText fileDescription;
    private String  fileKey;
    public static final int REQUEST_CODE = 123;
    private File file;
    private File fileCopy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);

        filename = findViewById(R.id.fileName);
        fileDescription = findViewById(R.id.fileDisc);

        Button uploadBtn = findViewById(R.id.uplod_btn);
        uploadBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // If the user doesn't pick a file just return
        if (requestCode != REQUEST_CODE || resultCode != RESULT_OK) {
            return;
        }
//        System.out.println("***************************  type : "+ data.getType());
        // Import the file
        try {
            importFile(data.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

            uploadInputStream(data.getData());
        }

    }

    public void importFile(Uri uri) throws IOException {
        String fileName = getFileName(uri);
        file = new File(uri.getPath());
        // The temp file could be whatever you want
//        fileCopy = copyToTempFile(uri,  file);

        // Done!

    }

    private String getFileName(Uri uri) throws IllegalArgumentException {
        // Obtain a cursor with information regarding this uri
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor.getCount() <= 0) {
            cursor.close();
            throw new IllegalArgumentException("Can't obtain file name, cursor is empty");
        }

        cursor.moveToFirst();

        String fileName = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));

        cursor.close();

        return fileName;
    }


    private File copyToTempFile(Uri uri, File tempFile) throws IOException {
        // Obtain an input stream from the uri
        InputStream inputStream = getContentResolver().openInputStream(uri);

        if (inputStream == null) {
            throw new IOException("Unable to obtain input stream from URI");
        }

        // Copy the stream to the temp file
//        FileUtils.copyStreamToFile(inputStream, tempFile);
        return tempFile;
    }


    private void uploadInputStream(Uri uri) {
        try {
            InputStream exampleInputStream = getContentResolver().openInputStream(uri);
            fileKey = UUID.randomUUID().toString();
            Amplify.Storage.uploadInputStream(
                    fileKey,
                    exampleInputStream,
                    result -> {

                        Material item = Material.builder()
                                .fileName(filename.getText().toString())
                                .fileDis(fileDescription.getText().toString())
                                .fileUrl(fileKey)
                                .fileMajor("Engineering")
                                .build();


                        Amplify.API.mutate(ModelMutation.create(item),  success -> Log.i(TAG, "Saved item: " ),
                                error -> Log.e(TAG, "Could not save item to DataStore", error));

                        Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey());

                    },
                    storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
            );
        }  catch (FileNotFoundException error) {
            Log.e("MyAmplifyApp", "Could not find file to open for input stream.", error);
        }

    }
}