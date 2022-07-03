package com.example.socialuniversityapp.recycler_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.MajorLike;
import com.amplifyframework.datastore.generated.model.MajorPost;
import com.example.socialuniversityapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MajorPostAdapter extends RecyclerView.Adapter<MajorPostAdapter.PostViewHolder> {

    private static final String TAG = UniversityPostAdapter.class.getSimpleName();
    List<MajorPost> postList ;
    ClickListener listener;
    private Boolean flag;
    private int likesCount;
    private String authUserId;
    Context recContext;
    private Handler handler;
    private String imageKey;

    public MajorPostAdapter(List<MajorPost> postList, ClickListener listener) {
        this.postList = postList;
        this.listener = listener;
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        recContext=parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.post_row, parent, false);
        return new PostViewHolder(listItemView, listener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        // fetch the current authenticated user ID and name
        Amplify.Auth.fetchUserAttributes(
                attributes -> {
                    Log.i("AuthDemo", "User attributes = " + attributes.toString());
                    attributes.forEach(authUserAttribute -> {
                        if (authUserAttribute.getKey().getKeyString().equals("sub"))
                        {
                            authUserId=authUserAttribute.getValue();
                            Amplify.API.query(ModelQuery.list(MajorLike.class,MajorLike.MAJOR_POST_LIKES_ID.eq(postList.get(position).getId())),
                                    likeSuccess -> {
                                        if (likeSuccess.hasData())
                                        {
                                            // check if user liked the post before by searching about the user id in the like rows
                                            for (MajorLike postLike : likeSuccess.getData())
                                            {
                                                if (postLike.getMajorUserId().equals(authUserId)){
                                                    holder.like_ic.setColorFilter(ContextCompat.getColor(recContext
                                                            ,R.color.design_default_color_primary_variant), PorterDuff.Mode.MULTIPLY);

                                                }
                                            }
                                        }
                                    },
                                    likeFailure -> {
                                        Log.e(TAG, "Failed to fetch the likes ",likeFailure);
                                    });
                        }
                    });
                },
                error -> Log.e("AuthDemo", "Failed to fetch user attributes.", error)
        );

        holder.stdName.setText(postList.get(position).getUserName());
        holder.postTime.setText(postList.get(position).getCreatedAt().format().substring(postList.get(position).getCreatedAt().format().indexOf('T')+1,postList.get(position).getCreatedAt().format().indexOf('.')-3)+" "+postList.get(position).getCreatedAt().toDate().toString().substring(4,9).replace(" ","/"));
        holder.postDesc.setText(postList.get(position).getBody());
        if (postList.get(position).getImage() != null)
        {
            imageKey = postList.get(position).getImage();
            Amplify.Storage.getUrl(
                    imageKey+".jpg",
                    success ->{
                        Bundle bundle = new Bundle();
                        bundle.putString("url", success.getUrl().toString());

                        Message message = new Message();
                        message.setData(bundle);

                        handler.sendMessage(message);

                        Log.i(TAG, "image " + success.getUrl());
                    },
                    error -> {
                        Log.i(TAG, "image Error : " + error);
                    });

            // Handler
            handler = new Handler(Looper.getMainLooper(), msg -> {
                String imageUrl = msg.getData().getString("url");
                holder.postImg.setVisibility(View.VISIBLE);
                Picasso.get().load(imageUrl).into(holder.postImg);
                return true;
            });


        }
        holder.postLikes.setText(postList.get(position).getLikes().size()+" Like");
        holder.postComments.setText(postList.get(position).getComments().size()+" Comment");

        holder.like_ic.setOnClickListener(view -> {
            flag = false;
            likesCount=0;

            // fetch like table rows that have the current post ID
            Amplify.API.query(ModelQuery.list(MajorLike.class,MajorLike.MAJOR_POST_LIKES_ID.eq(postList.get(position).getId())),
                    likeSuccess -> {
                        if (likeSuccess.hasData())
                        {
                            // check if user liked the post before by searching about the user id in the like rows
                            for (MajorLike postLike : likeSuccess.getData())
                            {
                                likesCount++;
                                if (postLike.getMajorUserId().equals(authUserId)){
                                    flag = true;
                                    holder.like_ic.setColorFilter(ContextCompat.getColor(recContext
                                            ,R.color.like_color), PorterDuff.Mode.MULTIPLY);
                                    Amplify.API.mutate(ModelMutation.delete(postLike),
                                            response -> Log.i(TAG, "like with id: " + response.getData().getId()),
                                            error -> Log.e(TAG, "delete failed", error)
                                    );

                                }
                            }
                        }
                        if (flag){
                            likesCount-=1;
                            holder.postLikes.setText(likesCount+" Like");
                        }
                        // if the user didn't like the post before
                        if (!flag)
                        {
                            // create an Instance of the Like model
                            MajorLike like=MajorLike.builder()
                                    .majorUserId(authUserId)
                                    .majorPostLikesId(postList.get(position).getId())
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
                                        Log.i(TAG, "Saved item: " + success.getData().getMajorUserId());
                                    },
                                    error -> {
                                        Log.e(TAG, "Could not save item to API", error);
                                    }
                            );
                            holder.like_ic.setColorFilter(ContextCompat.getColor(recContext
                                    , R.color.design_default_color_primary_variant),PorterDuff.Mode.MULTIPLY);

                            // update the likes count in the post
                            likesCount++;
                            holder.postLikes.setText(likesCount+" Like");
                        }
                    },
                    likeFailure -> {
                        Log.e(TAG, "Failed to fetch the likes ",likeFailure);
                    });
        });

    }

    // Return the size of the post list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return postList.size();
    }

    // Provide a reference to the type of views
    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView stdName;
        TextView postTime;
        TextView postDesc;
        TextView postLikes;
        TextView postComments;
        ImageView postImg;
        ImageButton like_ic;
        ImageButton comment_ic;


        ClickListener listener;

        public PostViewHolder(@NonNull View itemView, ClickListener listener) {
            super(itemView);

            this.listener = listener;

            stdName=itemView.findViewById(R.id.uniUsername);
            postTime=itemView.findViewById(R.id.uniTime);
            postDesc=itemView.findViewById(R.id.postDesc);
            postImg=itemView.findViewById(R.id.post_img);
            postLikes=itemView.findViewById(R.id.post_like);
            postComments=itemView.findViewById(R.id.post_comment);
            like_ic=itemView.findViewById(R.id.like_ic);
            comment_ic=itemView.findViewById(R.id.comment_ic);


            like_ic.setOnClickListener(view -> {
                listener.onPostItemLikeClicked(getAdapterPosition());
            });
            comment_ic.setOnClickListener(view -> {
                listener.onPostItemCommentClicked(getAdapterPosition());
            });
            postImg.setOnClickListener(view ->{
                listener.onPostItemImageClicked(getAdapterPosition());
            });
            stdName.setOnClickListener(view ->{
                listener.onPostItemUserNameClicked(getAdapterPosition());
            });
        }
    }
    public interface ClickListener {
        void onPostItemLikeClicked(int position);
        void onPostItemCommentClicked(int position);
        void onPostItemImageClicked(int position);
        void onPostItemUserNameClicked(int position);
    }

}
