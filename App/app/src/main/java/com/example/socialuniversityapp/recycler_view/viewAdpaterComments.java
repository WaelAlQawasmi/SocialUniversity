package com.example.socialuniversityapp.recycler_view;

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
    public static List<Comment> dataList;
    CustomClickListener listener;

    public viewAdpaterComments(List<Comment> datalist) {
        this.dataList=datalist;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.comments_row, parent, false);
        return new CustomViewHolder(listItemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Comment comment=dataList.get(position);
       holder.CommentUsername.setText(comment.getCommentUserName());
        holder.CommentContent.setText(comment.getContent());
//        holder.CommentTime.setText(comment.getCreatedAt());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView CommentUsername;
    TextView CommentTime;
    TextView CommentContent;
    CustomClickListener listener;
    public CustomViewHolder(@NonNull View itemView, CustomClickListener listener) {
        super(itemView);
        this.listener = listener;
//        username = itemView.findViewById(R.id.title);
//        describtion = itemView.findViewById(R.id.state);
        CommentUsername = itemView.findViewById(R.id.CommentUsername);
        CommentContent = itemView.findViewById(R.id.CommentContent);
        CommentTime = itemView.findViewById(R.id.CommentTime);
        itemView.setOnClickListener(view -> this.listener.onCommentDataItemClicked(getAdapterPosition()));

    }
}
interface CustomClickListener {
    void onCommentDataItemClicked(int position);
}