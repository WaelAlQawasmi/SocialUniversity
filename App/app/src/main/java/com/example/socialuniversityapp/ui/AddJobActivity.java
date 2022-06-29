package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Job;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.recycler_view.JobRecyclerView;

public class AddJobActivity extends AppCompatActivity {

    private static final String TAG = AddJobActivity.class.getSimpleName();
    private EditText mBodyText, mPhoneText, mAddressText;
    private AutoCompleteTextView mAutoCompleteTextView;
    private Button mAddJobButton;
    private ProgressBar mProgressBar;

    String titleValue;

    String[] items = {"Marketing", "Science, Engineering, and Math", "Law, Public Safety,", "Business and Finance", "Health Science", "Information Technology"};
    ArrayAdapter<String> adapterItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        // Inflate
        mAutoCompleteTextView = findViewById(R.id.auto_completed_text);
        mBodyText = findViewById(R.id.add_job_body);
        mPhoneText = findViewById(R.id.add_job_phone);
        mAddressText = findViewById(R.id.add_job_address);
        mAddJobButton = findViewById(R.id.job_add_button);
        mProgressBar = findViewById(R.id.add_job_progress);


        adapterItem = new ArrayAdapter<>(this, R.layout.list_item, items);
        mAutoCompleteTextView.setAdapter(adapterItem);

        // auto Complete Click
        mAutoCompleteTextView.setOnItemClickListener(autoCompleteTextViewClick);

        // Add Job Button Click
        mAddJobButton.setOnClickListener(addJobButtonClick);

    }

    // auto Complete Click
    private final AdapterView.OnItemClickListener autoCompleteTextViewClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            char currentValue = adapterView.getItemAtPosition(position).toString().charAt(0);
            switch (currentValue){
                case 'M':
                case 'S':
                case 'L':
                case 'B':
                case 'H':
                case 'I':
                    titleValue = adapterView.getItemAtPosition(position).toString();
                    break;

            }

        }
    };

    // Add Job Button Click
    private final View.OnClickListener addJobButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            mProgressBar.setVisibility(View.VISIBLE);
            Job job = Job.builder()
                    .name(titleValue)
                    .body(mBodyText.getText().toString())
                    .phone(mPhoneText.getText().toString())
                    .address(mAddressText.getText().toString())
                    .build();
            // Save to the backend
            Amplify.API.mutate(ModelMutation.create(job),
                    success -> {
                        Log.i(TAG, "Add Job Success");
                        startActivity(new Intent(AddJobActivity.this, JobRecyclerView.class));
                    },
                    error -> {
                    });
            mProgressBar.setVisibility(View.INVISIBLE);
        }

    };

}