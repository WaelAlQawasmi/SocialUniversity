package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.adapter.fragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class materialAndPostsActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_and_posts);

        tabLayout=findViewById(R.id.tabLayout);
        viewPager =findViewById(R.id.viewPager);

        tabLayout.setupWithViewPager(viewPager);
        fragmentAdapter fragmentAdapter=new fragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentAdapter.addFragment(new MaterialActivity(),"material");
        fragmentAdapter.addFragment(new MajorPostActivity(),"major");
        fragmentAdapter.addFragment(new UniversityPostActivity(),"university");
        viewPager.setAdapter(fragmentAdapter);
    }
}