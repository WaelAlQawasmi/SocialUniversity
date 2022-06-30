package com.example.socialuniversityapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.service.carrier.CarrierMessagingService;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.MajorPost;
import com.amplifyframework.datastore.generated.model.UniPost;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.recycler_view.MajorPostAdapter;
import com.example.socialuniversityapp.recycler_view.UniversityPostAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MajorPostActivity extends Fragment {

    private static final String TAG = MajorPostActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private FloatingActionButton mFloatingActionButton;

    List<MajorPost> majorPostList = new ArrayList<>();
    private Handler handler;

    private String majorUser;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_major_post, container, false);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inflate
        mRecyclerView = view.findViewById(R.id.major_post_recycler);
        mFloatingActionButton = view.findViewById(R.id.major_floating_action_button);

        mFloatingActionButton.setOnClickListener(mFloatingActionButtonClick);


        // Fetch Authentication User From Cloud
        Amplify.Auth.fetchUserAttributes(
                attributes -> {
                    Log.i("AuthDemo", "User attributes = " + attributes.toString());

                    attributes.forEach(authUserAttribute -> {

                        if (authUserAttribute.getKey().getKeyString().equals("nickname"))
                            majorUser=authUserAttribute.getValue();

                    });
                },
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );
        fetchPostFromAPI(view);
    }


    View.OnClickListener mFloatingActionButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity().getApplicationContext(), AddPostActivity.class);
            intent.putExtra("context", "Major");
            startActivity(intent);
        }

    };
    public void fetchPostFromAPI(View view){
        Log.i(TAG, "User Major: "+ majorUser);
        Amplify.API.query(ModelQuery.list(MajorPost.class),
                success -> {

                        for (MajorPost majorPost : success.getData()) {
//                            if (majorPost.getMajor().equals(majorUser))
                            majorPostList.add(majorPost);
                        }

                    Bundle bundle = new Bundle();
                    bundle.putString("postsList", success.toString());

                    Message message = new Message();
                    message.setData(bundle);

                    handler.sendMessage(message);
                },
                error -> {
                    Log.e(TAG, "Could not query Api", error);
                });

        handler = new Handler(Looper.getMainLooper(), msg -> {
            mRecyclerView = view.findViewById(R.id.major_post_recycler);

            Log.i(TAG, "fetchPostFromAPI: "+ majorPostList);
            // defining action to the like and comment buttons
            MajorPostAdapter postRecyclerViewAdapter = new MajorPostAdapter(majorPostList, new MajorPostAdapter.ClickListener() {
                @Override
                public void onPostItemLikeClicked(int position) {

                }

                @Override
                public void onPostItemCommentClicked(int position) {

                }
            });
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            mRecyclerView.setAdapter(postRecyclerViewAdapter);
            mRecyclerView.setHasFixedSize(true);


            return true;
        });

    }
}