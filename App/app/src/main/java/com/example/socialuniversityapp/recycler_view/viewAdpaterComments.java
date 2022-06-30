package com.example.socialuniversityapp.recycler_view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Comment;
import com.example.socialuniversityapp.R;

import java.util.List;

public class viewAdpaterComments extends RecyclerView.Adapter<CustomViewHolder> {
    List<Comment> commentList;

    public viewAdpaterComments(List<Comment> commentList) {
        this.commentList=commentList;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.comments_row, parent, false);
        return new CustomViewHolder(listItemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Comment comment=commentList.get(position);
       holder.CommentUsername.setText(comment.getCommentUserName());
        holder.CommentContent.setText(comment.getContent());
        holder.CommentTime.setText(comment.getCreatedAt().format().substring(commentList.get(position).getCreatedAt().format().indexOf('T')+1,commentList.get(position).getCreatedAt().format().indexOf('.')-3)+" "+commentList.get(position).getCreatedAt().toDate().toString().substring(4,9).replace(" ","/"));

    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }
}
class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView CommentUsername;
    TextView CommentTime;
    TextView CommentContent;

    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        CommentUsername = itemView.findViewById(R.id.comment_name);
        CommentContent = itemView.findViewById(R.id.comment_text);
        CommentTime = itemView.findViewById(R.id.comment_time);


    }
}
