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
import com.amplifyframework.datastore.generated.model.User;
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
    private String authUserId;
    private String nickNameUser;
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

                        if (authUserAttribute.getKey().getKeyString().equals("custom:majoreName"))
                            majorUser=authUserAttribute.getValue();
                        Log.i(TAG, "User Major in auth : "+ majorUser);
                        if (authUserAttribute.getKey().getKeyString().equals("sub"))
                        {
                            authUserId=authUserAttribute.getValue();
                        }
                        if (authUserAttribute.getKey().getKeyString().equals("nickname"))
                        {
                            nickNameUser=authUserAttribute.getValue();
                        }

                    });
                    fetchPostFromAPI(majorUser);

                },
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );
    }


    View.OnClickListener mFloatingActionButtonClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getActivity().getApplicationContext(), AddPostActivity.class);
            intent.putExtra("context", "Major");
            startActivity(intent);
        }

    };
    public void fetchPostFromAPI(String majorUser){
        Log.i(TAG, "User Major in api : "+ majorUser);
        Amplify.API.query(ModelQuery.list(MajorPost.class, MajorPost.MAJOR.contains(majorUser)),
                posts -> {
                    for(MajorPost majorposts:posts.getData()){
                        majorPostList.add(majorposts);
                    }



                    Bundle bundle = new Bundle();
                    bundle.putString("postsList", posts.toString());

                    Message message = new Message();
                    message.setData(bundle);

                    handler.sendMessage(message);
                },
                error -> {
                    Log.e(TAG, "Could not query Api", error);
                });

        handler = new Handler(Looper.getMainLooper(), msg -> {


            Log.i(TAG, "fetchPostFromAPI: "+ majorPostList);
            // defining action to the like and comment buttons
            MajorPostAdapter postRecyclerViewAdapter = new MajorPostAdapter(majorPostList, new MajorPostAdapter.ClickListener() {
                @Override
                public void onPostItemLikeClicked(int position) {

                }

                @Override
                public void onPostItemCommentClicked(int position) {
                    Intent intent=new Intent(getActivity(),MajorCommentActivity.class);
                    intent.putExtra("postId",majorPostList.get(position).getId());
                    intent.putExtra("userName",nickNameUser);
                    intent.putExtra("authUserId",authUserId);
                    startActivity(intent);
                }

                @Override
                public void onPostItemImageClicked(int position) {
                    Intent userProfile = new Intent(getActivity().getApplicationContext(), users_profile.class);
                    userProfile.putExtra("userId", majorPostList.get(position).getId());
                    startActivity(userProfile);
                }

                @Override
                public void onPostItemUserNameClicked(int position) {
                    Intent userProfile = new Intent(getActivity().getApplicationContext(), users_profile.class);
                    userProfile.putExtra("username", majorPostList.get(position).getUserName());
                    startActivity(userProfile);
                }
            });


            mRecyclerView.setAdapter(postRecyclerViewAdapter);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


            return true;
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        List<MajorPost> onResumePostList=new ArrayList<>();

        Amplify.API.query(ModelQuery.list(MajorPost.class, MajorPost.MAJOR.contains(majorUser)),
                posts -> {
                    for(MajorPost majorposts:posts.getData()){
                        onResumePostList.add(majorposts);
                    }



                    Bundle bundle = new Bundle();
                    bundle.putString("postsList", posts.toString());

                    Message message = new Message();
                    message.setData(bundle);

                    handler.sendMessage(message);
                },
                error -> {
                    Log.e(TAG, "Could not query Api", error);
                });

        handler = new Handler(Looper.getMainLooper(), msg -> {


            Log.i(TAG, "fetchPostFromAPI: "+ onResumePostList);
            // defining action to the like and comment buttons
            MajorPostAdapter postRecyclerViewAdapter = new MajorPostAdapter(onResumePostList, new MajorPostAdapter.ClickListener() {
                @Override
                public void onPostItemLikeClicked(int position) {

                }

                @Override
                public void onPostItemCommentClicked(int position) {
                    Intent intent=new Intent(getActivity(),MajorCommentActivity.class);
                    intent.putExtra("postId",onResumePostList.get(position).getId());
                    intent.putExtra("userName",nickNameUser);
                    intent.putExtra("authUserId",authUserId);
                    startActivity(intent);
                }

                @Override
                public void onPostItemImageClicked(int position) {
                    Intent userProfile = new Intent(getActivity().getApplicationContext(), users_profile.class);
                    userProfile.putExtra("userId", onResumePostList.get(position).getId());
                    startActivity(userProfile);
                }

                @Override
                public void onPostItemUserNameClicked(int position) {
                    Intent userProfile = new Intent(getActivity().getApplicationContext(), users_profile.class);
                    userProfile.putExtra("username", onResumePostList.get(position).getUserName());
                    startActivity(userProfile);
                }
            });


            mRecyclerView.setAdapter(postRecyclerViewAdapter);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


            return true;
        });
    }
}