package com.example.socialuniversityapp.ui;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
import android.widget.Button;
import android.widget.ImageButton;
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
import java.util.Objects;

public class UniversityPostActivity extends Fragment {

    private static final String TAG = UniversityPostActivity.class.getSimpleName();
    RecyclerView mRecyclerView;
    private Handler handler;
    private String authUserId;
    private Boolean flag;
    private int likesCount;
    private String nickNameUser;
    private FloatingActionButton mFloatingActionButton;
    private ImageButton like_ic;
    private ImageButton comment_ic;
    List<UniPost> uniPostList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_university_post, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        uniPostList=new ArrayList<>();
        mRecyclerView=view.findViewById(R.id.uniPosts);

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


        // defining action to the like and comment buttons
        UniversityPostAdapter postRecyclerView = new UniversityPostAdapter(uniPostList, new UniversityPostAdapter.ClickListener() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onPostItemLikeClicked(int position) {



            }

            @Override
            public void onPostItemCommentClicked(int position) {
                Intent intent=new Intent(getActivity(),CommentActivity.class);
                intent.putExtra("postId",uniPostList.get(position).getId());
                intent.putExtra("userName",nickNameUser);
                intent.putExtra("authUserId",authUserId);
                startActivity(intent);
            }

            @Override
            public void onPostItemImageClicked(int position) {

            }

            @Override
            public void onPostItemUserNameClicked(int position) {

            }
        });

        mRecyclerView.setAdapter(postRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return true;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        List<UniPost> onResumePostList=new ArrayList<>();
        Amplify.API.query(ModelQuery.list(UniPost.class),
                success -> {
                    if (success.hasData()) {
                        for (UniPost uniPost : success.getData()) {
                            onResumePostList.add(uniPost);
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

        handler = new Handler(Looper.getMainLooper(), msg -> {


            // defining action to the like and comment buttons
            UniversityPostAdapter postRecyclerView = new UniversityPostAdapter(onResumePostList, new UniversityPostAdapter.ClickListener() {
                @SuppressLint("SetTextI18n")
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onPostItemLikeClicked(int position) {



                }

                @Override
                public void onPostItemCommentClicked(int position) {
                    Intent intent=new Intent(getActivity(),CommentActivity.class);
                    intent.putExtra("postId",onResumePostList.get(position).getId());
                    intent.putExtra("userName",nickNameUser);
                    intent.putExtra("authUserId",authUserId);
                    startActivity(intent);
                }

                @Override
                public void onPostItemImageClicked(int position) {

                }

                @Override
                public void onPostItemUserNameClicked(int position) {

                }
            });

            mRecyclerView.setAdapter(postRecyclerView);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            return true;
        });
    }
}