package com.example.socialuniversityapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;
import com.amplifyframework.datastore.generated.model.chat;
import com.example.socialuniversityapp.R;
import com.example.socialuniversityapp.recycler_view.messageRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class messages extends AppCompatActivity {
String TAG=messages.class.getSimpleName();
    List<chat> chatsDataList = new ArrayList();
    List<User> UsersDataList = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_messages);


        getUsersAlreadyTalkedTo();
//        chat chat1=chat.builder()
//                .chatFirstUserId("56510c0f-ad04-4812-bc59-4302240828bf")
//                .chatSecondUserId("e21eb46b-b03c-4cf8-bb84-2577d6454e82")
//                .build();
//        Amplify.API.mutate(ModelMutation.create(chat1), f->{
//            Log.i(TAG,"worked");
//        },error->{
//            Log.i(TAG,"failed");
//
//        });

    }



    public void getUsersAlreadyTalkedTo(){

        RecyclerView recyclerView = findViewById(R.id.UsersMessages);


        String current_user_email=Amplify.Auth.getCurrentUser().getUsername();



        Amplify.API.query(ModelQuery.list(chat.class),chats ->{

            if(chats.hasData()) {

                Amplify.API.query(ModelQuery.list(User.class,User.EMAIL.contains(current_user_email)),users ->{
                    String current_user_id="";
                    if(users.hasData()){

                        for(User user:users.getData()){
                            current_user_id=user.getId();
                        }

                for (chat chat : chats.getData()) {
                    checkTheUsersAndAddHisChats(chat,current_user_id);

                }

                runOnUiThread(()->{

                    recyclerView.setAdapter(messageRecyclerViewMethod());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                });

                    }
                },error->{
                    Log.i(TAG,"error to get the users are related to chat");

                });
                Log.i(TAG,"there is a chats");

            }
                },error ->{
            Log.e(TAG,"error to get the chats");

                });


//        Amplify.DataStore.clear(
//                () -> Log.i(TAG, "DataStore is cleared."),
//                failure -> Log.e(TAG, "Failed to clear DataStore.")
//                );



}

    private messageRecyclerView messageRecyclerViewMethod() {
        messageRecyclerView customRecyclerViewAdapter=new messageRecyclerView(
                UsersDataList,position -> {
            Log.i(TAG,"test");
            User user=UsersDataList.get(position);

            Intent chatActivity=new Intent(this, chatsActivity.class);
            chatActivity.putExtra("id",user.getId());
            chatActivity.putExtra("cognitoid",user.getCognitoId());
            chatActivity.putExtra("email",user.getEmail());
            chatActivity.putExtra("name",user.getName());
            startActivity(chatActivity);
        }
        ) ;
        return customRecyclerViewAdapter;
    }

    private void checkTheUsersAndAddHisChats(chat chat,String current_user_id) {
        if (chat.getChatFirstUserId().equals(current_user_id) ||
                chat.getChatSecondUserId().equals(current_user_id)) {
            chatsDataList.add(chat);

            if(!chat.getChatFirstUserId().equals(current_user_id)){
                UsersDataList.add(chat.getFirstUser());
            }
            else{
                UsersDataList.add(chat.getSecondUser());

            }
        }
    }
}