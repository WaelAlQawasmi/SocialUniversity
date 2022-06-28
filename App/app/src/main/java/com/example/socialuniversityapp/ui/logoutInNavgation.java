package com.example.socialuniversityapp.ui;

import static android.content.Context.MODE_PRIVATE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amplifyframework.core.Amplify;
import com.example.socialuniversityapp.R;

public class logoutInNavgation extends Fragment {
    private static final String TAG =logoutInNavgation.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root  = inflater.inflate(R.layout.activity_logout_in_navgation, container, false);
        logout();

        return root;

    }
    private void logout() {
        SharedPreferences preferences=this.getActivity().getSharedPreferences("checkbox",MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("remember","false");
        editor.apply();
        Amplify.Auth.signOut(
                () -> {
                    Log.i(TAG, "Signed out successfully");
                    startActivity(new Intent(this.getActivity(), LoginActivity.class));
                    authSession("logout");
                    this.getActivity().finish();
                },
                error -> Log.e(TAG, error.toString())
        );

    }
    private void authSession(String method) {
        Amplify.Auth.fetchAuthSession(
                result -> Log.i(TAG, "Auth Session => " + method + result.toString()),
                error -> Log.e(TAG, error.toString())
        );
    }
}