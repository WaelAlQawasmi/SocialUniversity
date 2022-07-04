package com.example.socialuniversityapp.recycler_view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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

public class JobRecyclerView extends Fragment {

    private RecyclerView mRecyclerView;
    private static final String TAG = "JobRecyclerView";
    List<Job> jobList = new ArrayList<>();
    Handler handler;

    private FloatingActionButton mFloatingActionButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_job_recycler, container, false);

    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.jobs_recycler);
        mFloatingActionButton = view.findViewById(R.id.floating_action_button);

        mFloatingActionButton.setOnClickListener(view2 -> {
            navigateToAddJob();
            startActivity(new Intent(this.getActivity(), AddJobActivity.class));
        });

        Amplify.API.query(ModelQuery.list(Job.class),
                success -> {
                    for (Job job:success.getData()) {
                        JobData newJob = new JobData(job.getName(),job.getBody(),job.getPhone(),job.getAddress());
                        jobList.add(job);
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

    public void setRecyclerJob(List<Job> newJobList){
        JobAdapter adapter = new JobAdapter(newJobList, new JobAdapter.ClickListener() {
            @Override
            public void onTaskItemClicked(int position) {
                Toast.makeText(getActivity().getApplicationContext(), "you clicked :  ", Toast.LENGTH_SHORT).show();
            }
        }) ;
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity().getApplicationContext()));

    }

    private void navigateToAddJob() {
        Intent addJobActivity = new Intent(this.getActivity(), AddJobActivity.class);
        startActivity(addJobActivity);
    }

    @Override
    public void onResume() {
        super.onResume();
        Amplify.API.query(ModelQuery.list(Job.class),
                success -> {
                    for (Job job:success.getData()) {
                        JobData newJob = new JobData(job.getName(),job.getBody(),job.getPhone(),job.getAddress());
                        jobList.add(job);
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
}
