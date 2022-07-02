package com.example.socialuniversityapp.recycler_view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.MajorComment;
import com.example.socialuniversityapp.R;

import java.util.List;

public class MajorCommentsAdapter extends RecyclerView.Adapter<majorCommentViewHolder>{
    List<MajorComment> majorCommentList;

    public MajorCommentsAdapter(List<MajorComment> commentList) {
        this.majorCommentList=commentList;
    }

    @NonNull
    @Override
    public majorCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.comments_row, parent, false);
        return new majorCommentViewHolder(listItemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull majorCommentViewHolder holder, int position) {
        MajorComment comment=majorCommentList.get(position);
        holder.CommentUsername.setText(comment.getMajorCommentUserName());
        holder.CommentContent.setText(comment.getMajorContent());
        holder.CommentTime.setText(comment.getCreatedAt().format().substring(majorCommentList.get(position).getCreatedAt().format().indexOf('T')+1,majorCommentList.get(position).getCreatedAt().format().indexOf('.')-3)+" "+majorCommentList.get(position).getCreatedAt().toDate().toString().substring(4,9).replace(" ","/"));

    }
    @Override
    public int getItemCount() {
        return majorCommentList.size();
    }

}

class majorCommentViewHolder extends RecyclerView.ViewHolder {
    TextView CommentUsername;
    TextView CommentTime;
    TextView CommentContent;

    public majorCommentViewHolder(@NonNull View itemView) {
        super(itemView);

        CommentUsername = itemView.findViewById(R.id.comment_name);
        CommentContent = itemView.findViewById(R.id.comment_text);
        CommentTime = itemView.findViewById(R.id.comment_time);


    }
}
