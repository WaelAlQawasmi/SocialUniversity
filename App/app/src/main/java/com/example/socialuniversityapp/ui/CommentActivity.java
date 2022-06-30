package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Comment;
import com.amplifyframework.datastore.generated.model.Like;
import com.amplifyframework.datastore.generated.model.UniPost;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.recycler_view.viewAdpaterComments;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommentActivity extends AppCompatActivity {
    private static final String TAG = CommentActivity.class.getSimpleName();
    TextView mUserName, mPostTime, mPostDesc, mPostLikes, mPostComments;
    ImageButton mPostLike, mPostComment;
    ImageView mPostImg;
    RecyclerView mCommRecyclerView;
    private Handler handler;
    private Handler postHandler;
    private Handler handlerImg;
    private UniPost post;
    private Boolean flag;
    private int likesCount;
    private int commentsCount;
    List<Comment> CommentList;
    private String imageKey;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        mUserName=findViewById(R.id.uniUsername_comm);
        mPostTime=findViewById(R.id.uniTime_comm);
        mPostDesc=findViewById(R.id.postDesc_comm);
        mPostImg=findViewById(R.id.post_img_comm);
        mPostLikes=findViewById(R.id.post_like_comm);
        mPostComments=findViewById(R.id.post_comment_comm);
        mPostLike=findViewById(R.id.like_ic_comm);
        mPostComment=findViewById(R.id.comment_ic_comm);

        CommentList=new ArrayList<>();
        Intent intent=getIntent();
        String postId=intent.getStringExtra("postId");
        String post_User=intent.getStringExtra("userName");
        String authUserId=intent.getStringExtra("authUserId");

        Amplify.API.query(ModelQuery.list(UniPost.class),
                success -> {
                    if (success.hasData()) {
                        for (UniPost uniPost : success.getData()) {
                            if (uniPost.getId().equals(postId))
                            {
                                post=uniPost;
                            }
                        }
                    }
                    Bundle postBundle = new Bundle();
                    postBundle.putString("postsList", success.toString());

                    Message postMessage = new Message();
                    postMessage.setData(postBundle);

                    postHandler.sendMessage(postMessage);
                },
                error -> {
                    Log.e(TAG, "Could not query Api", error);
                });
        postHandler= new Handler(Looper.getMainLooper(),message -> {
            mUserName.setText(post.getUserName());
            mPostTime.setText(post.getCreatedAt().format().substring(post.getCreatedAt().format().indexOf('T')+1,post.getCreatedAt().format().indexOf('.')-3)+" "+post.getCreatedAt().toDate().toString().substring(4,9).replace(" ","/"));
            mPostDesc.setText(post.getBody());
            if (post.getImage() != null)
            {
                imageKey = post.getImage();
                Amplify.Storage.getUrl(
                        imageKey+".jpg",
                        success ->{
                            Bundle bundleImg = new Bundle();
                            bundleImg.putString("url", success.getUrl().toString());

                            Message messageImg = new Message();
                            messageImg.setData(bundleImg);

                            handlerImg.sendMessage(messageImg);

                            Log.i(TAG, "image " + success.getUrl());
                        },
                        error -> {
                            Log.i(TAG, "image Error : " + error);
                        });

                // Handler
                handlerImg = new Handler(Looper.getMainLooper(), msg -> {
                    String imageUrl = msg.getData().getString("url");
                    mPostImg.setVisibility(View.VISIBLE);
                    Picasso.get().load(imageUrl).into(mPostImg);
                    return true;
                });


            }
            mPostLikes.setText(post.getLikes().size()+" Like");
            mPostComments.setText(post.getComments().size()+" Comment");
            Amplify.API.query(ModelQuery.list(Like.class,Like.UNI_POST_LIKES_ID.eq(post.getId())),
                    likeSuccess -> {
                        if (likeSuccess.hasData())
                        {
                            // check if user liked the post before by searching about the user id in the like rows
                            for (Like postLike : likeSuccess.getData())
                            {
                                if (postLike.getUserId().equals(authUserId)){
                                    mPostLike.setColorFilter(ContextCompat.getColor(getApplicationContext()
                                            ,R.color.design_default_color_primary_variant), PorterDuff.Mode.MULTIPLY);

                                }
                            }
                        }
                    },
                    likeFailure -> {
                        Log.e(TAG, "Failed to fetch the likes ",likeFailure);
                    });
            return true;
        });

        Amplify.API.query(ModelQuery.list(Comment.class,Comment.UNI_POST_COMMENTS_ID.eq(postId)),
                success -> {
                    if (success.hasData()) {
                        for (Comment comm : success.getData()) {
                            commentsCount++;
                            CommentList.add(comm);
                        }
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("commentsList", success.toString());

                    Message message = new Message();
                    message.setData(bundle);

                    handler.sendMessage(message);
                },
                error -> {
                    Log.e(TAG, "Could not query Api", error);
                });

        handler = new Handler(Looper.getMainLooper(), msg -> {
            mCommRecyclerView=findViewById(R.id.commentRecyclerView);
            viewAdpaterComments commentsAdapter=new viewAdpaterComments(CommentList);

            mCommRecyclerView.setAdapter(commentsAdapter);
            mCommRecyclerView.setHasFixedSize(true);
            mCommRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            return true;
        });

        mPostComment.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
            builder.setTitle("Comment");
            builder.setMessage(" Write a comment ...");
            final EditText input = new EditText(CommentActivity.this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            input.setHint(" Write a comment...");
            input.setLayoutParams(lp);
            builder.setView(input);
            builder.setIcon(R.drawable.post_commet);
            builder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (input.getText() != null)
                    {
                        Comment comment = Comment.builder().commentUserName(post_User).content(input.getText().toString()).uniPostCommentsId(postId).build();
                        // save the Like instance
//                        Amplify.DataStore.save(comment,
//                                success -> {
//                                    Log.i(TAG, "Saved comment: " + success.item().getContent());
//                                },
//                                error -> {
//                                    Log.e(TAG, "Could not save item to DataStore", error);
//                                }
//                        );

                        Amplify.API.mutate(
                                ModelMutation.create(comment),
                                success -> {
                                    Log.i(TAG, "Saved comment: " + success.getData().getContent());
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            commentsCount++;
                                            mPostComments.setText(commentsCount+" Comment");
                                            onResume();
                                        }
                                    });
                                },
                                error -> {
                                    Log.e(TAG, "Could not save item to API", error);
                                }
                        );


                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();

        });

        mPostLike.setOnClickListener(view -> {
            flag = false;
            likesCount=0;

            // fetch like table rows that have the current post ID
            Amplify.API.query(ModelQuery.list(Like.class,Like.UNI_POST_LIKES_ID.eq(postId)),
                    likeSuccess -> {
                        if (likeSuccess.hasData())
                        {
                            // check if user liked the post before by searching about the user id in the like rows
                            for (Like postLike : likeSuccess.getData())
                            {
                                likesCount++;
                                if (postLike.getUserId().equals(authUserId)){
                                    flag = true;
                                    mPostLike.setColorFilter(ContextCompat.getColor(getApplicationContext()
                                            ,R.color.like_color), PorterDuff.Mode.MULTIPLY);
                                    Amplify.API.mutate(ModelMutation.delete(postLike),
                                            response -> {
                                                Log.i(TAG, "like with id: " + response.getData().getId());
                                            },
                                            error -> Log.e(TAG, "delete failed", error)
                                    );

                                }
                            }
                        }
                        if (flag){
                            likesCount-=1;
                            mPostLikes.setText(likesCount+" Like");
                        }
                        // if the user didn't like the post before
                        if (!flag)
                        {
                            // create an Instance of the Like model
                            Like like=Like.builder()
                                    .userId(authUserId)
                                    .uniPostLikesId(post.getId())
                                    .build();

                            // save the Like instance
//                                Amplify.DataStore.save(like,
//                                        success -> {
//                                            Log.i(TAG, "Saved like: " + success.item().getUserId());
//                                        },
//                                        error -> {
//                                            Log.e(TAG, "Could not save item to DataStore", error);
//                                        }
//                                );

                            Amplify.API.mutate(
                                    ModelMutation.create(like),
                                    success -> {
                                        Log.i(TAG, "Saved item: " + success.getData().getUserId());
                                    },
                                    error -> {
                                        Log.e(TAG, "Could not save item to API", error);
                                    }
                            );

                            mPostLike.setColorFilter(ContextCompat.getColor(getApplicationContext()
                                    , R.color.design_default_color_primary_variant),PorterDuff.Mode.MULTIPLY);

                            // update the likes count in the post
                            likesCount++;
                            mPostLikes.setText(likesCount+" Like");
                        }
                    },
                    likeFailure -> {
                        Log.e(TAG, "Failed to fetch the likes ",likeFailure);
                    });
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent=getIntent();
        String postId=intent.getStringExtra("postId");
        List<Comment> onResumeCommentList=new ArrayList<>();
        Amplify.API.query(ModelQuery.list(Comment.class,Comment.UNI_POST_COMMENTS_ID.eq(postId)),
                success -> {
                    if (success.hasData()) {
                        for (Comment comm : success.getData()) {
                            onResumeCommentList.add(comm);
                        }
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("commentsList", success.toString());

                    Message message = new Message();
                    message.setData(bundle);

                    handler.sendMessage(message);
                },
                error -> {
                    Log.e(TAG, "Could not query Api", error);
                });

        handler = new Handler(Looper.getMainLooper(), msg -> {
            mCommRecyclerView=findViewById(R.id.commentRecyclerView);
            viewAdpaterComments commentsAdapter=new viewAdpaterComments(onResumeCommentList);

            mCommRecyclerView.setAdapter(commentsAdapter);
            mCommRecyclerView.setHasFixedSize(true);
            mCommRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            return true;
        });
    }
}