package com.example.socialuniversityapp.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.Message;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.example.socialuniversityapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import android.widget.ArrayAdapter;

public class Profile extends Fragment {
    private static final String TAG = "Profile";
    public static final int REQUEST_CODE = 123;
    Handler handler;
    File file;
    String imageKey = null;
    String downImage;
    CircleImageView newImage;
    View root;
    private final MediaPlayer mp = new MediaPlayer();

    private String majorName;
    String[] items = {"Computer Science", "Software Eng", "AI", "Networking Sec", "Data Analysis", "Math", "Physics", "History"};
    ArrayAdapter<String> adapterItem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_profile, container, false);

        TextView name = root.findViewById(R.id.userName_profile);
        TextView email = root.findViewById(R.id.email_profile);
        TextView major = root.findViewById(R.id.major_prof);
        TextView uniID = root.findViewById(R.id.uniId);
        ImageView imageEdit = root.findViewById(R.id.edit);
        Button editDone = root.findViewById(R.id.editDone);
        EditText nameEdit = root.findViewById(R.id.userName_edit);
        EditText uniId_edit = root.findViewById(R.id.uniId_edit);
        AutoCompleteTextView autoCompleteTextView= root.findViewById(R.id.sign_up_major_text);


        imageEdit.setOnClickListener(view -> {
            // CHANge visibiltt if butones
            imageEdit.setVisibility(View.INVISIBLE);
            editDone.setVisibility(View.VISIBLE);
            // change visabilty of text viow
            name.setVisibility(View.INVISIBLE);
            uniID.setVisibility(View.INVISIBLE);
            major.setVisibility(View.INVISIBLE);

            // set and get text
            String filterId = (String) uniID.getText();
            nameEdit.setText(name.getText());
          //autoCompleteTextView.setText(major.getText());

            // CHANge visibility if buttons
            uniId_edit.setText(filterId.replace("ID : ", ""));
            root.findViewById(R.id.sign_up_major_text);
            nameEdit.setVisibility(View.VISIBLE);
            uniId_edit.setVisibility(View.VISIBLE);
            root.findViewById(R.id.major_edit).setVisibility(View.VISIBLE);



        });


        adapterItem = new ArrayAdapter<>(getActivity(), R.layout.list_item, items);
        autoCompleteTextView.setAdapter(adapterItem);


        // auto Complete Click
        final AdapterView.OnItemClickListener autoCompleteTextViewClick = new AdapterView.OnItemClickListener() {

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
        autoCompleteTextView.setOnItemClickListener(autoCompleteTextViewClick);


        editDone.setOnClickListener(view -> {


            List<AuthUserAttribute> attributes=new ArrayList<>();

            attributes.add(new AuthUserAttribute(AuthUserAttributeKey.nickname(), nameEdit.getText().toString()));
            attributes.add(new AuthUserAttribute(AuthUserAttributeKey.custom("custom:universityId"), uniId_edit.getText().toString()));
           // attributes.add(new AuthUserAttribute(AuthUserAttributeKey.custom("custom:majoreName"), majorName));
            Amplify.Auth.updateUserAttributes(
                    attributes  , // attributes is a list of AuthUserAttribute
                    result -> Log.i("AuthDemo", "Updated user attributes = " + result.toString()),
                    error -> Log.e("AuthDemo", "Failed to update user attributes.", error)
            );
          // fetchData();

            // CHANge visibiltt if butones
            imageEdit.setVisibility(View.VISIBLE);
            editDone.setVisibility(View.INVISIBLE);
            // change visabilty of text viow
            name.setVisibility(View.VISIBLE);
            uniID.setVisibility(View.VISIBLE);
            major.setVisibility(View.VISIBLE);

            String filterId = (String) uniID.getText();
            name.setText(nameEdit.getText());
            uniID.setText("ID : "+uniId_edit.getText());
            //autoCompleteTextView.setText(major.getText());

            nameEdit.setVisibility(View.INVISIBLE);
            uniId_edit.setVisibility(View.INVISIBLE);
            //  major_edit.setVisibility(View.INVISIBLE);
            root.findViewById(R.id.major_edit).setVisibility(View.INVISIBLE);


        });
//            Intent intent=new Intent(getActivity(),Profile.class);
//            startActivity(intent);
//        newImage = findViewById(R.id.imageView_profile);

        fetchData();

        handler = new Handler(Looper.getMainLooper(), msg -> {
            String newUser = msg.getData().getString("newUserName");
            String newEmail = msg.getData().getString("newEmail");
            String newMajor = msg.getData().getString("newMajor");
            String uniId = msg.getData().getString("UID");
            name.setText(newUser);
            email.setText(newEmail);
            major.setText(newMajor);
            uniID.setText("ID : " + uniId);
            return true;
        });

        FloatingActionButton iamgeBtn = root.findViewById(R.id.imgBtn);
        iamgeBtn.setOnClickListener(view2 -> imageUpload());

        TextView logout = root.findViewById(R.id.logout_profile);
        logout.setOnClickListener(view -> {
            logout();
        });
        return root;
    }

    private void fetchData() {
        Amplify.Auth.fetchUserAttributes(
                attributes -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("newUserName", attributes.get(3).getValue());
                    bundle.putString("newEmail", attributes.get(4).getValue());
                    bundle.putString("newMajor", attributes.get(5).getValue());
                    bundle.putString("UID", attributes.get(2).getValue());
                    Message message = new Message();
                    message.setData(bundle);
                    handler.sendMessage(message);
                },
                error -> Log.e(TAG, "can't find username", error)
        );
    }


    public void imageUpload() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            // Handle error
            Log.e(TAG, "onActivityResult: Error getting image from device");
            return;
        }

        switch (requestCode) {
            case REQUEST_CODE:
                // Get photo picker response for single select.
                Uri currentUri = data.getData();

                String imgName = root.findViewById(R.id.imageView_profile).toString();
                // Do stuff with the photo/video URI.
                Log.i(TAG, "the uri is => " + currentUri);

                try {
                    Bitmap bitmap = getBitmapFromUri(currentUri);

                    file = new File(getActivity().getFilesDir(), "profImg.jpg");
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
                                Log.i(TAG, "Successfully uploaded: " + result.getKey());
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
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();

        return image;
    }

    private void downloadImg(String imageKey) {
        Amplify.Storage.downloadFile(
                imageKey,
                new File(getActivity().getFilesDir() + "/" + imageKey),
                response -> {

                    Log.i(TAG, "Successfully downloaded: " + response.getFile().getName());
                    newImage = root.findViewById(R.id.imageView_profile);
                    Bitmap bitmap = BitmapFactory.decodeFile(getActivity().getApplicationContext().getFilesDir() + "/" + response.getFile().getName());
                    newImage.setImageBitmap(bitmap);

                },
                error -> Log.e(TAG, "Download Failure", error)
        );
    }

    private void playAudio(InputStream audioData) {
        File mp3File = new File(getActivity().getCacheDir(), "audio.mp3");

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
                    startActivity(new Intent(this.getActivity(), LoginActivity.class));
                    getAuthSession("logout");
                    getActivity().finish();
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