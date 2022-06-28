package com.example.socialuniversityapp.recycler_view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.UniPost;
import com.example.socialuniversityapp.R;

import java.util.List;

public class UniversityPostAdapter extends RecyclerView.Adapter<UniversityPostAdapter.PostViewHolder> {

    private static final String TAG = UniversityPostAdapter.class.getSimpleName();
    List<UniPost> postList;
    ClickListener listener;

    public UniversityPostAdapter(List<UniPost> postList, ClickListener listener) {
        this.postList = postList;
        this.listener = listener;
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.post_row, parent, false);
        return new PostViewHolder(listItemView, listener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        holder.stdName.setText(postList.get(position).getUserName());
        holder.postTime.setText(postList.get(position).getCreatedAt().toString());
        holder.postDesc.setText(postList.get(position).getBody());
        if (postList.get(position).getImage() != null)
        {
            // TODO: 6/24/2022 fetch the image from the url in the uniPost table
            holder.postImg.setImageResource(R.drawable.post_img);
        }
        holder.postLikes.setText(postList.get(position).getLikes().size()+" Like");
        holder.postComments.setText(postList.get(position).getComments().size()+" Comment");

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
        ImageView postImg;
        TextView postLikes;
        TextView postComments;
        Button like;
        Button comment;

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
            like=itemView.findViewById(R.id.like);
            comment=itemView.findViewById(R.id.comment);

            like.setOnClickListener(view -> {
                listener.onPostItemLikeClicked(getAdapterPosition());
            });
            comment.setOnClickListener(view -> {
                listener.onPostItemCommentClicked(getAdapterPosition());
            });

        }
    }
    public interface ClickListener {
        void onPostItemLikeClicked(int position);
        void onPostItemCommentClicked(int position);
    }

}

