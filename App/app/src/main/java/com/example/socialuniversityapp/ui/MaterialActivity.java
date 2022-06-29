package com.example.socialuniversityapp.ui;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;


import java.util.HashMap;

import com.example.socialuniversityapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MaterialActivity extends Fragment {
    private String username, imageKey;


    HashMap<String, String> teams = new HashMap<String, String>();
    public static final int REQUEST_CODE = 123;
    public static String IMGurl = "fileName";


    View root;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.activity_material, container, false);
        FloatingActionButton addJob =root.findViewById(R.id.addMaterialBtn);
        addJob.setOnClickListener(view -> {
            navigateToAddMaterial();
        });

        return root;

    }

    private void navigateToAddMaterial() {
        Intent addMaterialActivity = new Intent(getActivity(), AddMaterial.class);
        startActivity(addMaterialActivity);
    }







}