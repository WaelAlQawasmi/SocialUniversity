package com.example.socialuniversityapp.ui.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Job;
import com.example.socialuniversityapp.R;

public class JobDetails extends AppCompatActivity {

    private static final String TAG = "JobDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        Intent intent = getIntent();

        TextView mTitle = findViewById(R.id.title);
        TextView mBody = findViewById(R.id.body);
        TextView mPhone = findViewById(R.id.phone);
        TextView mAddress = findViewById(R.id.phone);

        Amplify.API.query(
                ModelQuery.list(Job.class, com.amplifyframework.datastore.generated.model.Job.ID.eq(intent.getStringExtra("id"))),
                response -> {
                    for (com.amplifyframework.datastore.generated.model.Job job : response.getData()) {
                        Log.i(TAG, "Job title from details  : " + job.getName());
                        mTitle.setText(job.getName());
                        mBody.setText(job.getBody());
                        mPhone.setText(job.getPhone());
                        mAddress.setText(job.getAddress());
                    }
                },
                error -> Log.e(TAG, "Query Failure !", error)
        );

    }
}
