package com.example.socialuniversityapp.recycler_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.User;
import com.example.socialuniversityapp.R;

import org.w3c.dom.Text;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class messageRecyclerView extends RecyclerView.Adapter<messageRecyclerView.MessagesViewHolder> {
    public static List<User> dataList;
    CustomClickListener listener;
    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItemView = layoutInflater.inflate(R.layout.messages_row, parent, false);
        return new MessagesViewHolder(listItemView, listener);
    }
    public messageRecyclerView(List<User> dataList, CustomClickListener listener) {
        this.dataList=dataList;
        this.listener = listener;
    }
    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        User user=dataList.get(position);
        holder.username.setText(user.getName());
    }

    @Override
    public int getItemCount() {
        return  dataList.size();
    }
    static class MessagesViewHolder extends RecyclerView.ViewHolder {
     TextView username;
     CircleImageView image;


        CustomClickListener listener;

        public MessagesViewHolder(@NonNull View itemView, CustomClickListener listener) {
            super(itemView);
            this.listener = listener;
            username = itemView.findViewById(R.id.MessageUsername);
            image = itemView.findViewById(R.id.profile_img);
            itemView.setOnClickListener(view -> this.listener.OnUserMessageClicked(getAdapterPosition()));
        }
    }
    public interface CustomClickListener {
        void OnUserMessageClicked(int position);
    }
}
