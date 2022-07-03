package com.example.socialuniversityapp.recycler_view;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amplifyframework.api.graphql.GraphQLOperation;
import com.amplifyframework.api.graphql.PaginatedResult;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Message;

import com.amplifyframework.datastore.generated.model.User;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.ui.MajorPostActivity;


import java.util.List;

public class chatAdapter extends ArrayAdapter<Message> {
    private static final String TAG = chatAdapter.class.getSimpleName();
    View view;
    LayoutInflater layoutInflater;
    boolean isCurrentUser;

    public chatAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      Message message=getItem(position);

        getTheCurrentId();

        SharedPreferences preferences=getContext().getSharedPreferences("user",MODE_PRIVATE);
        String current_user_id=preferences.getString("current_user_id","");


        isCurrentUser = message.getMessageUserId().equals(current_user_id);

        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (isCurrentUser) {

                view = layoutInflater.inflate(R.layout.message_sent_layout, parent, false);

            } else {

                view = layoutInflater.inflate(R.layout.message_recieve_layout, parent, false);

                TextView nameText = view.findViewById(R.id.name);

                nameText.setText(message.getUser().getName());

            }


            TextView messageContentText = view.findViewById(R.id.message);

            TextView messageDateText = view.findViewById(R.id.messageDate);

            messageContentText.setText(message.getContent());

            messageDateText.setText(message.getDate().format());





        return view;
}
public void getTheCurrentId(){
    Amplify.API.query(ModelQuery.list(User.class,User.EMAIL.contains(Amplify.Auth.getCurrentUser().getUsername())), users ->{

        if(users.hasData()){
            for(User user:users.getData()){
                String current_user_id=user.getId();
                SharedPreferences preferences=getContext().getSharedPreferences("user",MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("current_user_id",current_user_id);
                editor.apply();
            }
        }},error->{

    });
}

}
