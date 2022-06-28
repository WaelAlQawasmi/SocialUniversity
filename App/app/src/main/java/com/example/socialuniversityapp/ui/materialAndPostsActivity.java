package com.example.socialuniversityapp.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.adapter.fragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class materialAndPostsActivity extends Fragment  {
    private static final String TAG = materialAndPostsActivity.class.getSimpleName();
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private String []titles=new String[]{"Material","Major","University"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root  = inflater.inflate(R.layout.activity_material_and_posts, container, false);


        tabLayout = root.findViewById(R.id.tabLayout);
        viewPager = root.findViewById(R.id.viewPager);

//        tabLayout.setupWithViewPager(viewPager);


        fragmentAdapter fragmentAdapter = new fragmentAdapter(this.getActivity());
//        fragmentAdapter.addFragment(new MaterialActivity(), "material");
//        fragmentAdapter.addFragment(new MajorPostActivity(), "major");
//        fragmentAdapter.addFragment(new UniversityPostActivity(), "university");
        viewPager.setAdapter(fragmentAdapter);

        new TabLayoutMediator(tabLayout,viewPager,(tab,position)->{
tab.setText(titles[position]);
        }).attach();
        return root;
    }




    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }



}
