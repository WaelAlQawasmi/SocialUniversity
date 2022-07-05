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


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

//                TextView nameText = view.findViewById(R.id.name);

//                nameText.setText(message.getUser().getName());

            }


            TextView messageContentText = view.findViewById(R.id.message);

            TextView messageDateText = view.findViewById(R.id.messageDate);

            messageContentText.setText(message.getContent());

            messageDateText.setText(substring(message.getDate().format()));





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
public String substring(String date){
String current=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());
     String result="";
    int currentMonth=Integer.parseInt(current.substring(5,7));
    int messageMonth=Integer.parseInt(date.substring(5,7));
    int currentDay=Integer.parseInt(current.substring(8,10));
    int messageDay=Integer.parseInt(date.substring(8,10));

    int currentHour=Integer.parseInt(current.substring(11,13));
    int messageHour=Integer.parseInt(date.substring(11,13));
    int currentMin=Integer.parseInt(current.substring(14,16));
    int messageMin=Integer.parseInt(date.substring(14,16));
     if(Math.abs(currentMonth-messageMonth)>0){
         result=Math.abs(currentMonth-messageMonth)+" month ago";
     }
     else if(true){
         if(Math.abs(currentDay-messageDay)>1){
             result=Math.abs(currentDay-messageDay)+" day ago";

         }
         else if(Math.abs(currentDay-messageDay)==1){
             result="yesterday";
         }
         else if(Math.abs(currentDay-messageDay)==0){
                 if(Math.abs(currentHour-messageHour)>0){
                     result=Math.abs(currentHour-messageHour)+" hour ago";
                 }
                 else if(Math.abs(currentHour-messageHour)==0){
                     if(Math.abs(currentMin-messageMin)>0){
                         result=Math.abs(currentMin-messageMin)+" min ago";
                     }
                 }

         }
     }

  return result;
}

}
