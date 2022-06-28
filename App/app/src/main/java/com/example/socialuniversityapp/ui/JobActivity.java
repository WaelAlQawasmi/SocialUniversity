
package com.example.socialuniversityapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.socialuniversityapp.R;


public class JobActivity extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root  = inflater.inflate(R.layout.activity_job_recycler, container, false);



        return root;
    }

    

}