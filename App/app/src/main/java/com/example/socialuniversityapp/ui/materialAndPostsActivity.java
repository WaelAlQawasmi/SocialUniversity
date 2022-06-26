package com.example.socialuniversityapp.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.amplifyframework.core.Amplify;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.adapter.fragmentAdapter;
import com.example.socialuniversityapp.databinding.ActivityNavagationBinding;
import com.example.socialuniversityapp.databinding.FragmentHomeBinding;
import com.example.socialuniversityapp.ui.ui.home.HomeViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class materialAndPostsActivity extends AppCompatActivity {
    private static final String TAG = materialAndPostsActivity.class.getSimpleName();
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button logout;
    private Button profile;
    private FragmentHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_and_posts);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        logout = findViewById(R.id.logout);
        tabLayout.setupWithViewPager(viewPager);
        profile=findViewById(R.id.profile);

        fragmentAdapter fragmentAdapter = new fragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        fragmentAdapter.addFragment(new MaterialActivity(), "material");
        fragmentAdapter.addFragment(new MajorPostActivity(), "major");
        fragmentAdapter.addFragment(new UniversityPostActivity(), "university");
        viewPager.setAdapter(fragmentAdapter);

        logout.setOnClickListener(view -> {
            SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("remember","false");
            editor.apply();
            Amplify.Auth.signOut(
                    () -> {
                        Log.i(TAG, "Signed out successfully");
                        startActivity(new Intent(materialAndPostsActivity.this, LoginActivity.class));
                        authSession("logout");
                        finish();
                    },
                    error -> Log.e(TAG, error.toString())
            );


        });

        profile.setOnClickListener(view ->{
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        });
    }
        private void authSession(String method) {
            Amplify.Auth.fetchAuthSession(
                    result -> Log.i(TAG, "Auth Session => " + method + result),
                    error -> Log.e(TAG, error.toString())
            );
        }


}