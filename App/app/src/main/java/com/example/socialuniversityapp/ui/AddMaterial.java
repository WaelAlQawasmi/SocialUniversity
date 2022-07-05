package com.example.socialuniversityapp.ui;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;

import com.amplifyframework.datastore.generated.model.Material;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import com.example.socialuniversityapp.R;


public class AddMaterial extends AppCompatActivity {

    private EditText filename;
    private AutoCompleteTextView mAutoDescription;
    private ImageView mUploadImage;
    private String  fileKey = "";
    public static final int REQUEST_CODE = 123;
    private File file;
    private File fileCopy;
    String extension="";


    String fileDescriptionSel = "", majorName = "";

    String[] items = {"Specialty Materials", "Help Materials", "University Materials", "College Materials"};
    ArrayAdapter<String> adapterItem;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_material);

        filename = findViewById(R.id.fileName);
        mAutoDescription = findViewById(R.id.auto_completed_text);
        mUploadImage = findViewById(R.id.add_material_upload);

        getMajorNameForAuth();

        Button uploadBtn = findViewById(R.id.upload_material_button);

        mUploadImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(intent, REQUEST_CODE);
        });

        uploadBtn.setOnClickListener(view ->{




            if (!filename.getText().toString().equals("")  && !fileDescriptionSel.equals("") && !fileKey.equals("")) {
                Material item = Material.builder()
                        .fileName(filename.getText().toString())
                        .fileDis(fileDescriptionSel)
                        .fileUrl(fileKey)
                        .fileMajor(majorName)
                        .fileExtension(extension)
                        .build();


                Amplify.API.mutate(ModelMutation.create(item), success -> Log.i(TAG, "Saved item: "),
                        error -> Log.e(TAG, "Could not save item to DataStore", error));
            } else {
                Toast.makeText(this, "Fill The missing Fields", Toast.LENGTH_SHORT).show();
            }
        });



        adapterItem = new ArrayAdapter<>(this, R.layout.list_item, items);
        mAutoDescription.setAdapter(adapterItem);

        // auto Complete Click
        mAutoDescription.setOnItemClickListener(mAutoDescriptionClick);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getMajorNameForAuth(){
        Amplify.Auth.fetchUserAttributes(
                attributes -> {
                    Log.i("AuthDemo", "User attributes = " + attributes.toString());
                    attributes.forEach(authUserAttribute -> {

                        if (authUserAttribute.getKey().getKeyString().equals("custom:majoreName"))
                            majorName=authUserAttribute.getValue();

                    });
                },
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );

    }
    // auto Complete Click
    private final AdapterView.OnItemClickListener mAutoDescriptionClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            char currentValue = adapterView.getItemAtPosition(position).toString().charAt(0);
            switch (currentValue){
                case 'S':
                case 'H':
                case 'U':
                case 'C':
                    fileDescriptionSel = adapterView.getItemAtPosition(position).toString();
                    break;

            }

        }
    };
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
            String extensionType = getContentResolver().getType(data.getData());
            Log.i(TAG, "****************** type  :"+ extensionType);
            switch (extensionType) {
                case "application/msword":
                    extension=".docx";
                    break;
                case "application/pdf":
                    extension=".pdf";
                    break;
                case "binary/octet-stream":
                    extension=".pptx";
                    break;
                case "image/png":
                    extension=".png";
                    break;
                case "image/jpeg":
                    extension=".jpeg";
                    break;
            }
            Log.i(TAG, "uploaded  ext   : "+  extension);
            uploadInputStream(data.getData(), extension);
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



    private void uploadInputStream(Uri uri, String extension) {
        try {
            InputStream exampleInputStream = getContentResolver().openInputStream(uri);
            fileKey = UUID.randomUUID().toString();
            Amplify.Storage.uploadInputStream(
                    fileKey + extension,
                    exampleInputStream,
                    result -> {
                        Log.i("MyAmplifyApp", "Successfully uploaded: " + result.getKey());

                    },
                    storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
            );
        }  catch (FileNotFoundException error) {
            Log.e("MyAmplifyApp", "Could not find file to open for input stream.", error);
        }

    }
//    public void changUploadBotonColor() {
//        Button upload =findViewById(R.id.uplod_btn);
//        upload.setText("uploded!");
//        upload.setBackgroundColor(this.getResources().getColor(R.color.error_color));
//    }
}