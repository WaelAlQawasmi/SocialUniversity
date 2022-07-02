package com.example.socialuniversityapp.recycler_view;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;

import android.os.Handler;

import java.util.ArrayList;

import java.util.List;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Material;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.adapter.MaterialAdapter;
import com.example.socialuniversityapp.data.JobData;
import com.example.socialuniversityapp.ui.AddMaterial;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MaterialRecyclerView extends Fragment {

    private Handler handler;
    List<Material> materialsList;

    public static String IMGurl = "fileName";


    View root;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("hello");
        materialsList = new ArrayList<>();
        root = inflater.inflate(R.layout.activity_material_rec_view, container, false);
        FloatingActionButton addJob =root.findViewById(R.id.addMaterialBtn);
        addJob.setOnClickListener(view -> {
            navigateToAddMaterial();
        });

        Amplify.API.query(ModelQuery.list(Material.class),
                success -> {

                    for (Material material :success.getData()) {
//                        Material newMaterial = new Material(material.getId(),material.getFileName(),material.getFileUrl(), material.getFileMajor());
                        materialsList.add(material);

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

        handler = new Handler(Looper.getMainLooper(), msg -> {
            RecyclerView recyclerView = root.findViewById(R.id.material_recycler_view);
            MaterialAdapter customRecyclerViewAdapter = new MaterialAdapter(materialsList, new MaterialAdapter.CustomClickListener() {
                @Override
                public void onTaskClicked(int position) {

                }
            });
//                    position -> {
//                Toast.makeText(
//                        MaterialRecyclerView,
//                        "you clicked :  " + materialsList.get(position).getFileName(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(getApplicationContext(), taskDetails.class);
//                intent.putExtra("id", materialsList.get(position).getId());
//                System.out.println("tasks list DB : " + materialsList);
//                startActivity(intent);
//            }) {
//                @Override
//                public void onTaskItemClicked(int position) {
//
//                }
//            };

            recyclerView.setAdapter(customRecyclerViewAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            return true;

        });

        return root;

    }

    private void navigateToAddMaterial() {
        Intent addMaterialActivity = new Intent(getActivity(), AddMaterial.class);
        startActivity(addMaterialActivity);
    }
}





