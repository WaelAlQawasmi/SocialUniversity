package com.example.socialuniversityapp.recycler_view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Job;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.adapter.JobAdapter;
import com.example.socialuniversityapp.data.JobData;
import com.example.socialuniversityapp.ui.AddJobActivity;
//import com.example.socialuniversityapp.ui.JobDetails;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class JobRecyclerView extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private static final String TAG = "JobRecyclerView";
    List<JobData> jobList = new ArrayList<>();
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_recycler);
        mRecyclerView = findViewById(R.id.jobs_recycler);

        FloatingActionButton addJob = findViewById(R.id.floating_action_button);
        addJob.setOnClickListener(view -> {
            navigateToAddJob();
        });

        Amplify.API.query(ModelQuery.list(Job.class),
                success -> {
                    for (Job job:success.getData()) {
                        JobData newJob = new JobData(job.getName(),job.getBody(),job.getPhone(),job.getAddress());
                        jobList.add(newJob);
                    }
                     Bundle bundle = new Bundle();
                    bundle.putString("success","success");

                    Message message = new Message();
                    message.setData(bundle);

                    handler.sendMessage(message);

                },
                failure -> {
                    Log.i("Amplify", "failed to query .");
                }
        );

                handler= new Handler(Looper.getMainLooper(),Msg ->{
            setRecyclerJob(jobList);
            return true;
        });
    }

    public void setRecyclerJob(List<JobData> newJobList){
//        JobAdapter adapter = new JobAdapter(newJobList, new JobAdapter.ClickListener() {
        JobAdapter adapter = new JobAdapter(
                newJobList, position ->  {
            Toast.makeText(
                    JobRecyclerView.this,
                    "you clicked :  " + newJobList.get(position).getTitle(), Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getApplicationContext(), JobDetails.class);
//            intent.putExtra("id", newJobList.get(position).getId());
//            System.out.println("Job list DB : "+ newJobList);
//            startActivity(intent);
        }){
//            @Override
            public void onTaskItemClicked(int position) {

            }
        };
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void navigateToAddJob() {
        Intent addJobActivity = new Intent(this, AddJobActivity.class);
        startActivity(addJobActivity);
    }
}
