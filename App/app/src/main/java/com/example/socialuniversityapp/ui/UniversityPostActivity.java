package com.example.socialuniversityapp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Like;
import com.amplifyframework.datastore.generated.model.UniPost;
import com.example.socialuniversityapp.R;

import com.example.socialuniversityapp.recycler_view.UniversityPostAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.List;

public class UniversityPostActivity extends Fragment {

    private static final String TAG = UniversityPostActivity.class.getSimpleName();
    RecyclerView mRecyclerView;
    private Handler handler;
    private String authUserId;
    private Boolean flag;
    private int likesCount;
    private String nickNameUser;
    private FloatingActionButton mFloatingActionButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_university_post, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<UniPost> uniPostList=new ArrayList<>();

        // fetch the current authenticated user ID and name
        Amplify.Auth.fetchUserAttributes(
                attributes -> {
                    Log.i("AuthDemo", "User attributes = " + attributes.toString());
                    attributes.forEach(authUserAttribute -> {
                        if (authUserAttribute.getKey().getKeyString().equals("sub"))
                        {
                            authUserId=authUserAttribute.getValue();
                        }
                        if (authUserAttribute.getKey().getKeyString().equals("nickname"))
                        {
                            nickNameUser=authUserAttribute.getValue();
                        }
                    });
                },
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );

        //
        Amplify.API.query(ModelQuery.list(UniPost.class),
                success -> {
                    if (success.hasData()) {
                        for (UniPost uniPost : success.getData()) {
                            uniPostList.add(uniPost);
                        }
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
        // move to add post Activity
        mFloatingActionButton = view.findViewById(R.id.floating_action_button);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (getActivity(), AddPostActivity.class));
            }
        });
        handler = new Handler(Looper.getMainLooper(), msg -> {
        mRecyclerView = view.findViewById(R.id.uniPosts);


        // defining action to the like and comment buttons
        UniversityPostAdapter postRecyclerView = new UniversityPostAdapter(uniPostList, new UniversityPostAdapter.ClickListener() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onPostItemLikeClicked(int position) {

                flag = false;
                likesCount=0;

                // fetch like table rows that have the current post ID
                Amplify.API.query(ModelQuery.list(Like.class,Like.UNI_POST_LIKES_ID.eq(uniPostList.get(position).getId())),
                        likeSuccess -> {
                            if (likeSuccess.hasData())
                            {
                                // check if user liked the post before by searching about the user id in the like rows
                                for (Like postLike : likeSuccess.getData())
                                {
                                    likesCount++;
                                    if (postLike.getUserId().equals(authUserId)){
                                        flag = true;
                                        // change the button
                                        Button bLike = view.findViewById(R.id.like);
                                        bLike.setText("Liked");
                                        bLike.setEnabled(false);
                                        // TODO: 6/25/2022 disLike -> delete the current like row from the table
                                    }
                                }
                            }
                            // if the user didn't like the post before
                            if (!flag)
                            {
                                // create an Instance of the Like model
                                Like like=Like.builder()
                                        .userId(authUserId)
                                        .uniPostLikesId(uniPostList.get(position).getId())
                                        .build();

                                // save the Like instance
                                Amplify.DataStore.save(like,
                                        success -> {
                                            Log.i(TAG, "Saved like: " + success.item().getUserId());
                                        },
                                        error -> {
                                            Log.e(TAG, "Could not save item to DataStore", error);
                                        }
                                );

                                Amplify.API.mutate(
                                        ModelMutation.create(like),
                                        success -> {
                                            Log.i(TAG, "Saved item: " + success.getData().getUserId());
                                        },
                                        error -> {
                                            Log.e(TAG, "Could not save item to API", error);
                                        }
                                );

                                // update the likes count in the post
                                likesCount++;
                                TextView pLikes = view.findViewById(R.id.post_like);
                                pLikes.setText(likesCount+" Like");
                            }
                        },
                        likeFailure -> {
                            Log.e(TAG, "Failed to fetch the likes ",likeFailure);
                        });

            }

            @Override
            public void onPostItemCommentClicked(int position) {
                Intent intent=new Intent(getActivity(),CommentActivity.class);
                intent.putExtra("postId",uniPostList.get(position).getId());
                intent.putExtra("userName",nickNameUser);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(postRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return true;
        });
    }
}